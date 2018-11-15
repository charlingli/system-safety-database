/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.searchObject;
import customObjects.validateIdObject;
import entities.DbUser;
import entities.DbwfHeader;
import entities.DbwfLine;
import entities.DbwfLinePK;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author David Ortega
 */
@Stateless
public class DbwfHeaderFacade extends AbstractFacade<DbwfHeader> implements DbwfHeaderFacadeLocal {

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    @EJB
    private DbwfLineFacadeLocal dbwfLineFacade;
    @EJB
    private DbglobalIdFacadeLocal dbglobalIdFacade;

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbwfHeaderFacade() {
        super(DbwfHeader.class);
    }

    @Override
    public validateIdObject newWorkFlow(List<DbUser> listApprovers, DbwfHeader wfHeaderObj, String autoNoId) {
        //Validate received fields
        validateIdObject tmpValidator;
        tmpValidator = this.checkGlobalId(autoNoId);
        if (!tmpValidator.isValidationFlag()) {
            return tmpValidator;
        }

        //Create the global consecutive
        tmpValidator = this.createConsecutive(autoNoId);
        if (!tmpValidator.isValidationFlag()) {
            return tmpValidator;
        } else {
            wfHeaderObj.setWfId(tmpValidator.getAnswerString());
        }

        //Populate wf header and line tables
        this.create(wfHeaderObj);
        Integer linNumber = 1;
        for (DbUser tmpUser : listApprovers) {
            DbwfLine tmpWfLine = new DbwfLine();
            DbwfLinePK tmpwfLinePk = new DbwfLinePK(wfHeaderObj.getWfId(), linNumber.toString());
            tmpWfLine.setDbwfLinePK(tmpwfLinePk);
            tmpWfLine.setWfUserIdApprover(tmpUser);
            linNumber += 1;
            dbwfLineFacade.create(tmpWfLine);
        }
        return new validateIdObject(true, tmpValidator.getAnswerString());
    }

    @Override
    //Validates if the transaction should be approved. Returns true in case the final approvation have been given.
    public boolean approvalProcess(DbwfHeader wfHeaderObj, String apprType) {
        //Because the obj could be just populated with the id field, the function will bring from the db.
        List<DbwfHeader> workObjList = this.findByName("wfId", wfHeaderObj.getWfId());
        DbwfHeader workObj = workObjList.get(0);
        //if the approval type is Admin Approved the transaction will directly approved.
        if (apprType.equals("adminApproval")) {
            //Because the workflow was approved by the admin the header control fields will change
            workObj.setWfStatusBefUpdate(workObj.getWfStatus());
            workObj.setWfStatus("A");
            DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
            workObj.setWfUserIdUpdate(activeUser);
            java.util.Date currentDate = new java.util.Date();
            workObj.setWfUpdatedDateTime(currentDate);
            this.edit(workObj);
            //Send to the completion method
            wfCompleteAction(workObj, "Approved");
            return true;
        } else if (apprType.equals("userApproval")) {
            //Validate if the wf meets conditions, if so the wf will be sent to the completion method.
            if (validateWfApprovalConditions(workObj)) {
                //Because the workflow is complete and approved, the status will change to approved 'A'
                workObj.setWfStatus("A");
                this.edit(workObj);
                //Send to the approval method
                wfCompleteAction(workObj, "Approved");
                return true;
            }
        }
        return false;
    }

    @Override
    //Validates if the transaction should be rejected. Returns true in case the final rejection have been given.
    public boolean rejectionProcess(DbwfHeader wfHeaderObj, String rjtcdType) {
        //Because the obj could be just populated with the id field, the function will bring from the db.
        List<DbwfHeader> workObjList = this.findByName("wfId", wfHeaderObj.getWfId());
        DbwfHeader workObj = workObjList.get(0);
        //if the rejectedType type is Admin rejected the transaction will directly rejected.
        if (rjtcdType.equals("adminApproval")) {
            //Because the workflow was rejected by the admin the header control fields will change
            workObj.setWfStatusBefUpdate(workObj.getWfStatus());
            workObj.setWfStatus("R");
            DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
            workObj.setWfUserIdUpdate(activeUser);
            java.util.Date currentDate = new java.util.Date();
            workObj.setWfUpdatedDateTime(currentDate);
            this.edit(workObj);
            //Send to the completion method
            wfCompleteAction(workObj, "Rejected");
            return true;
        } else if (rjtcdType.equals("userApproval")) {
            //Validate if the wf meets conditions, if so the wf will be sent to the completion method.
                if (validateWfRejectionConditions(workObj, "R")) {
                    //Because the workflow is complete and rejected, the status will change to rejected 'R'
                    workObj.setWfStatus("R");
                    this.edit(workObj);
                    //Send to the approval method
                    wfCompleteAction(workObj, "Rejected");
                    return true;
                }
            }
        return false;
    }

    @Override
    //Calls when a user cancels their own workflow, bypassing all other approvers, unlike rejection.
    public boolean cancellationProcess(DbwfHeader wfHeaderObj) {
        //Because the obj could be just populated with the id field, the function will bring from the db.
        List<DbwfHeader> workObjList = this.findByName("wfId", wfHeaderObj.getWfId());
        DbwfHeader workObj = workObjList.get(0);
        //The transaction will directly rejected.
        workObj.setWfStatusBefUpdate(workObj.getWfStatus());
        workObj.setWfStatus("C");
        DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        workObj.setWfUserIdUpdate(activeUser);
        java.util.Date currentDate = new java.util.Date();
        workObj.setWfUpdatedDateTime(currentDate);
        this.edit(workObj);
        return true;
    }

    @Override
    //Validates if the transaction should be reviewed. Returns true in case the final revision have been given.
    public boolean reviewProcess(DbwfHeader wfHeaderObj, String rwvType) {
        //Because the obj could be just populated with the id field, the function will bring from the db.
        List<DbwfHeader> workObjList = this.findByName("wfId", wfHeaderObj.getWfId());
        DbwfHeader workObj = workObjList.get(0);
        if (rwvType.equals("adminApproval")) {
            //Because the workflow was rejected by the admin the header control fields will change
            workObj.setWfStatusBefUpdate(workObj.getWfStatus());
            workObj.setWfStatus("I");
            DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
            workObj.setWfUserIdUpdate(activeUser);
            java.util.Date currentDate = new java.util.Date();
            workObj.setWfUpdatedDateTime(currentDate);
            this.edit(workObj);
            //Send to the completion method
            //wfCompleteAction(workObj, "Review");
            return true;
        } else if (rwvType.equals("userApproval")) {
            //Validate if the wf meets conditions, if so the wf will be sent to the completion method.
                if (validateWfRejectionConditions(workObj, "I")) {
                    //Because the workflow is complete and will be send back, the status will change to review 'I'
                    workObj.setWfStatus("I");
                    this.edit(workObj);
                    //Send to the approval method
                    //wfCompleteAction(workObj, "Review");
                    return true;
                }
            }
        return false;
    } 

    @Override
    //This method will check all the work flow implemented types.
    public boolean wfTypesValidation(String wfTypeName) {
        switch (wfTypeName) {
            case "All approve":
                return true;
            case "Majority approve":
                return true;
            case "First to approve":
                return true;
            default:
                System.out.println("ejb.DbwfHeaderFacade.wfTypesValidation() -> The required type " + wfTypeName + " is not implemented!");
                break;
        }
        return false;
    }

    private validateIdObject checkGlobalId(String stringToCheck) {
        String[] parts = stringToCheck.split("-");
        if (parts.length > 3) {
            return new validateIdObject(false, "The consecutive must have less than 3 sections.");
        } else {
            String pattern = "^([A-Za-z0-9]){1,10}$";
            for (String tmp : parts) {
                if (!Pattern.matches(pattern, tmp)) {
                    return new validateIdObject(false, "The consecutive section " + tmp + " is empty or it's length is larger than 10 characters.");
                }
            }
        }
        return new validateIdObject(true, "");
    }

    private validateIdObject createConsecutive(String stringToCreate) {
        String[] parts = stringToCreate.split("-");
        validateIdObject result = new validateIdObject();
        switch (parts.length) {
            case 1:
                return dbglobalIdFacade.nextConsecutive(parts[0], "-", 5);
            case 2:
                return dbglobalIdFacade.nextConsecutive(parts[0], parts[1], "-", 5);
            case 3:
                return dbglobalIdFacade.nextConsecutive(parts[0], parts[1], parts[2], "-", 5);
        }
        return new validateIdObject(false, "Method createConsecutive was unable to create the required consecutive.");
    }

    //This method will validate the particular wf conditions (wf business logic for approvals)
    private boolean validateWfApprovalConditions(DbwfHeader wfObj) {
        if (wfTypesValidation(wfObj.getWfTypeId().getWfTypeName())) {
            switch (wfObj.getWfTypeId().getWfTypeName()) {
                case "All approve":
                    if (dbwfLineFacade.validateAllApprovers(wfObj, "A")) {
                        return true;
                    }
                    break;
                case "Majority approve":
                    if (dbwfLineFacade.validate50plus1Approvers(wfObj, "A")) {
                        return true;
                    }
                    break;
                case "First to approve":
                    if (dbwfLineFacade.validateFirstApprover(wfObj, "A")) {
                        return true;
                    }
                    break;
                default:
                    System.out.println("ejb.DbwfHeaderFacade.validateWfConditions() -> The business logic for "
                            + "the required type " + wfObj.getWfTypeId().getWfTypeName() + " is not implemented!");
                    break;
            }
        }
        return false;
    }

    //This method will validate the particular wf conditions (wf business logic for rejections or revisions)
    //In case one approver rejectes the transaction in the type "All approve" that transaction won't have future concensus,
    //therefore the complete flow will rejecte in that case.
    private boolean validateWfRejectionConditions(DbwfHeader wfObj, String rejectOrReview) {
        if (wfTypesValidation(wfObj.getWfTypeId().getWfTypeName())) {
            switch (wfObj.getWfTypeId().getWfTypeName()) {
                case "Majority approve":
                    if (dbwfLineFacade.validate50plus1Approvers(wfObj, rejectOrReview)) {
                        return true;
                    }
                    break;
                case "All approve":
                case "First to approve":
                    if (dbwfLineFacade.validateFirstApprover(wfObj, rejectOrReview)) {
                        return true;
                    }
                    break;
                default:
                    System.out.println("ejb.DbwfHeaderFacade.validateWfRejectionConditions() -> The business logic for "
                            + "the required type " + wfObj.getWfTypeId().getWfTypeName() + "is not implemented!");
                    break;
            }
        }
        return false;
    }

    //This method will send the action to the related ejb
    private void wfCompleteAction(DbwfHeader wfObj, String finalDecision) {
        switch (wfObj.getWfCompleteMethod()) {
            case "HazardApprovalWF":
                dbHazardFacade.wfApproveHazard(wfObj.getWfObjectId(), finalDecision);
                break;
            case "HazardDeletionWF":
                if (finalDecision.equals("Approved")) {
                    dbHazardFacade.wfDeleteHazard(wfObj.getWfObjectId());
                }
                break;
        }
    }
    
    private String logicOperator(String relationType, int paramNo) {
        String finalSTR = "";
        paramNo++;
        switch (relationType) {
            case "=":
                finalSTR = " = ?" + paramNo + " ";
                break;
            case "like":
                finalSTR = " LIKE CONCAT('%', ?" + paramNo + ", '%') ";
                break;
            case "in":
                finalSTR = " IN ?" + paramNo + " ";
                break;
        }
        return finalSTR;
    }
    
    @Override
    public List<DbwfHeader> findWorkflowsByFieldsOnly(List<searchObject> workflowList) {
        String querySTR = "SELECT DISTINCT Wf FROM DbwfHeader Wf ";
        List<DbwfHeader> resultantList = new ArrayList<>();
        boolean flagEntities = true;
        boolean flagInitialCycle = true;

        //Adding the alias table according to each object in the list
        for (int x = 0; x < workflowList.size(); x++) {
            switch (workflowList.get(x).getEntity1Name()) {
                case "DbwfHeader":
                    workflowList.get(x).setTableAlias("Wf");
                    break;
                default:
                    flagEntities = false;
                    break;
            }
        }

        //if some unkown entity was introduced in the list, the function will not go to the next step.
        if (flagEntities) {
            //creating all the where criterias
            for (int x = 0; x < workflowList.size(); x++) {
                StringBuilder tmpString = new StringBuilder();

                if (flagInitialCycle) {
                    flagInitialCycle = false;
                    tmpString.append("WHERE ");
                } else {
                    tmpString.append("AND ");
                }

                if (workflowList.get(x).getEntity3Name() != null) {
                    tmpString.append(workflowList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(workflowList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(workflowList.get(x).getEntity3Name());
                    tmpString.append(".");
                    tmpString.append(workflowList.get(x).getFieldName());
                    tmpString.append(logicOperator(workflowList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                } else if (workflowList.get(x).getEntity2Name() != null) {
                    tmpString.append(workflowList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(workflowList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(workflowList.get(x).getFieldName());
                    tmpString.append(logicOperator(workflowList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                } else if (workflowList.get(x).getEntity1Name() != null) {
                    tmpString.append(workflowList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(workflowList.get(x).getFieldName());
                    tmpString.append(logicOperator(workflowList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                }
            }

            try {
                boolean flagParameters = true;
                Query query = em.createQuery(querySTR);
                for (int x = 0; x < workflowList.size(); x++) {
                    int paramNo = x + 1;
                    switch (workflowList.get(x).getFieldType()) {
                        case "string":
                            if ("=".equals(workflowList.get(x).getRelationType())
                                    || "like".equals(workflowList.get(x).getRelationType())) {
                                query.setParameter(paramNo, workflowList.get(x).getUserInput());
                            } else if ("in".equals(workflowList.get(x).getRelationType())) {
                                String parts[] = workflowList.get(x).getUserInput().split("\\,");
                                if (parts.length > 0) {
                                    List<String> inString = new ArrayList<>();
                                    inString.addAll(Arrays.asList(parts));
                                    query.setParameter(paramNo, inString);
                                } else {
                                    flagParameters = false;
                                }
                            }
                            break;
                        case "int":
                            if ("=".equals(workflowList.get(x).getRelationType())
                                    || "like".equals(workflowList.get(x).getRelationType())) {
                                query.setParameter(paramNo, Integer.parseInt(workflowList.get(x).getUserInput()));
                            } else if ("in".equals(workflowList.get(x).getRelationType())) {
                                String parts[] = workflowList.get(x).getUserInput().split("\\,");
                                if (parts.length > 0) {
                                    List<Integer> inInt = new ArrayList<>();
                                    for (String tmpString : parts) {
                                        Integer tmpVal = Integer.parseInt(tmpString);
                                        inInt.add(tmpVal);
                                    }
                                    query.setParameter(paramNo, inInt);
                                } else {
                                    flagParameters = false;
                                }
                            }
                            break;
                        case "date":
                            if ("=".equals(workflowList.get(x).getRelationType())) {
                                query.setParameter(paramNo, Date.valueOf(workflowList.get(x).getUserInput()));
                            }
                            break;
                        default:
                            flagParameters = false;
                            System.out.println("ejb.DbwfHeaderFacade.findWorkflowsByFieldsOnly(): "
                                    + "The fieldType is not allowed.");
                            break;
                    }
                }

                if (flagParameters) {
                    resultantList = query.getResultList();
                } else {
                    System.out.println("ejb.DbwfHeaderFacade.findWorkflowsByFieldsOnly(): "
                            + "The function could not find a string separed by commas in a filed marked as IN relationype.");
                }

            } catch (Exception e) {
                throw e;
            }

        } else {
            System.out.println("ejb.DbwfHeaderFacade.findWorkflowsByFieldsOnly(): "
                    + "Some entites could not be matched with the managed by this function.");
        }

        return resultantList;
    }
    
    @Override
    public List<DbwfHeader> findActiveByUser(int userId) {
        String queryStr;
        List<DbwfHeader> resultantList = new ArrayList<>();

        try {
            queryStr = "SELECT h FROM DbwfHeader h WHERE h.wfStatus = 'I' "
                    + " AND h.wfUserIdAdd.userId = ?1";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, userId);

            resultantList = query.getResultList();

        } catch (Exception e) {

            throw e;
        }
        return resultantList;       
    }
}