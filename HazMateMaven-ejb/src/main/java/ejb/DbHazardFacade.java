/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.searchObject;
import customObjects.treeNodeObject;
import customObjects.validateIdObject;
import entities.DbCause;
import entities.DbConsequence;
import entities.DbControl;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import entities.DbHazardSbs;
import entities.DbUser;
import entities.DbhazardSystemStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbHazardFacade extends AbstractFacade<DbHazard> implements DbHazardFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbHazardFacade() {
        super(DbHazard.class);
    }

    @Override
    public List<DbControlHazard> getControlHazard(String hazardId) {
        return em.createQuery("FROM DbControlHazard c WHERE c.dbHazard.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbControlHazard> getMControlHazard(String hazardId) {
        return em.createQuery("FROM DbControlHazard c WHERE c.dbHazard.hazardId = :checkHazardId AND c.controlType = 'M'")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbControlHazard> getPControlHazard(String hazardId) {
        return em.createQuery("FROM DbControlHazard c WHERE c.dbHazard.hazardId = :checkHazardId AND c.controlType = 'P'")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbControl> getControls(int controlId) {
        return em.createQuery("FROM DbControl c WHERE c.dbControl.controlId = :checkControlId")
                .setParameter("checkControlId", controlId)
                .getResultList();
    }

    @Override
    public List<DbConsequence> getConsequences(String hazardId) {
        return em.createQuery("FROM DbConsequence c WHERE c.consequenceHazardId.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbCause> getCauses(String hazardId) {
        return em.createQuery("FROM DbCause c WHERE c.causeHazardId.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbHazardSbs> getSbs(String hazardId) {
        return em.createQuery("FROM DbHazardSbs s WHERE s.dbHazard.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbHazardCause> getHazardCause(String hazardId) {
        return em.createQuery("FROM DbHazardCause c WHERE c.dbHazard.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbHazardConsequence> getHazardConsequence(String hazardId) {
        return em.createQuery("FROM DbHazardConsequence c WHERE c.dbHazard.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbHazard> validateHazardId(String hazardId) {
        return em.createQuery("FROM DbHazard h WHERE h.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public int calculateRiskScore(int frequencyScore, int severityScore) {
        int riskScore = frequencyScore * severityScore;
        if (riskScore == 0) {
            riskScore = frequencyScore - 1;
            if (riskScore == 0) {
                return 1;
            }
        }
        return riskScore;
    }

    @Override
    public List<DbHazard> getHazardsFromCause(int causeId) {
        return em.createQuery("SELECT h FROM DbHazard h WHERE EXISTS(SELECT 'x' "
                + "FROM DbHazardCause hc WHERE hc.dbHazard.hazardId = h.hazardId "
                + "AND hc.dbCause.causeId = ?1)")
                .setParameter(1, causeId)
                .getResultList();
    }

    @Override
    public List<DbHazard> getHazardsFromConsequence(int consequenceId) {
        return em.createQuery("SELECT h FROM DbHazard h WHERE EXISTS(SELECT 'x' "
                + "FROM DbHazardConsequence hc WHERE hc.dbHazard.hazardId = h.hazardId "
                + "AND hc.dbConsequence.consequenceId = ?1)")
                .setParameter(1, consequenceId)
                .getResultList();
    }

    @Override
    public List<DbHazard> getHazardsFromControl(int controlId) {
        return em.createQuery("SELECT h FROM DbHazard h WHERE EXISTS(SELECT 'x' "
                + "FROM DbControlHazard hc WHERE hc.dbHazard.hazardId = h.hazardId "
                + "AND hc.dbControl.controlId = ?1)")
                .setParameter(1, controlId)
                .getResultList();
    }

    @Override
    //This method consolidates all the previous methods to retrieve hazards data.
    //The rqstType parameter indicates ther type or requiered data. (A-All, C-Complete, I-Incomplete)
    //The searchType parameter indicates the type of result required. ("Normal" - "DefaultView")
    public List<Object> findHazards(List<searchObject> hazardList, List<treeNodeObject> sbsList, String rqstType, String srchType) {
        List<Object> resultantList = new ArrayList<>();

        if (rqstType.equals("")) {
            rqstType = "A";
        }
        validateIdObject queryString = createQueryString(hazardList, sbsList, rqstType, srchType);

        if (queryString.isValidationFlag()) {
            try {
                int curParamNo = 0;
                boolean flagParameters = true;
                List<String> inStringTree = new ArrayList<>();

                Query query = em.createQuery(queryString.getAnswerString());
                if (!hazardList.isEmpty()) {
                    for (int x = 0; x < hazardList.size(); x++) {
                        curParamNo++;
                        switch (hazardList.get(x).getFieldType()) {
                            case "string":
                                if ("=".equals(hazardList.get(x).getRelationType())
                                        || "like".equals(hazardList.get(x).getRelationType())) {
                                    query.setParameter(curParamNo, hazardList.get(x).getUserInput());
                                } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                    String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                    if (parts.length > 0) {
                                        List<String> inString = new ArrayList<>();
                                        inString.addAll(Arrays.asList(parts));
                                        query.setParameter(curParamNo, inString);
                                    } else {
                                        flagParameters = false;
                                    }
                                }
                                break;
                            case "int":
                                if ("=".equals(hazardList.get(x).getRelationType())
                                        || "like".equals(hazardList.get(x).getRelationType())) {
                                    query.setParameter(curParamNo, Integer.parseInt(hazardList.get(x).getUserInput()));
                                } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                    String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                    if (parts.length > 0) {
                                        List<Integer> inInt = new ArrayList<>();
                                        for (String tmpString : parts) {
                                            Integer tmpVal = Integer.parseInt(tmpString);
                                            inInt.add(tmpVal);
                                        }
                                        query.setParameter(curParamNo, inInt);
                                    } else {
                                        flagParameters = false;
                                    }
                                }
                                break;
                            default:
                                flagParameters = false;
                                break;
                        }
                    }
                }

                if (!sbsList.isEmpty()) {
                    for (treeNodeObject tmpTreeNode : sbsList) {
                        inStringTree.add(tmpTreeNode.getNodeId());
                    }
                    query.setParameter(curParamNo + 1, inStringTree);
                }

                if (flagParameters) {
                    resultantList = query.getResultList();
                }
            } catch (NumberFormatException e) {
            }
        }
        return resultantList;
    }

    //Performs the logic to construct the dynamic query
    private validateIdObject createQueryString(List<searchObject> hazardList, List<treeNodeObject> sbsList, String rqstType, String srchType) {
        String qrySelect = getSelectBySearchType(srchType);
        String qryFrom = "FROM DbHazard Haz ";
        String qryWhere = "";
        boolean flagEntities = true;

        //Adding the alias table according to each object in the list
        for (int x = 0; x < hazardList.size(); x++) {
            switch (hazardList.get(x).getEntity1Name()) {
                case "DbHazard":
                    hazardList.get(x).setTableAlias("Haz");
                    break;
                case "DbHazardCause":
                    hazardList.get(x).setTableAlias("HCau");
                    break;
                case "DbHazardConsequence":
                    hazardList.get(x).setTableAlias("HCoq");
                    break;
                case "DbControlHazard":
                    hazardList.get(x).setTableAlias("HCtl");
                    break;
                case "DbCause":
                    hazardList.get(x).setTableAlias("Cau");
                    break;
                case "DbConsequence":
                    hazardList.get(x).setTableAlias("Coq");
                    break;
                case "DbControl":
                    hazardList.get(x).setTableAlias("Ctl");
                    break;
                default:
                    flagEntities = false;
                    break;
            }
        }

        if (flagEntities) {
            if (rqstType.equals("A")) {
                if (!hazardList.isEmpty() || !sbsList.isEmpty()) {
                    twoStringObjs tmpResponse = constructFrom(qryFrom, qryWhere, hazardList, sbsList);
                    qryFrom = tmpResponse.fromField;
                    qryWhere = tmpResponse.whereField;
                    qryWhere = constructWhere(qryWhere, hazardList, sbsList);
                }
                return new validateIdObject(true, qrySelect + qryFrom + qryWhere);
            } else if (rqstType.equals("C")) {
                qryFrom = "FROM DbHazard Haz, DbHazardCause HCau, DbHazardConsequence HCoq, DbControlHazard HCtl, "
                        + "DbCause Cau, DbConsequence Coq, DbControl Ctl, DbHazardSbs HSbs ";
                qryWhere = "WHERE Haz.hazardId = HCau.dbHazardCausePK.hazardId "
                        + "AND HCau.dbHazardCausePK.causeId = Cau.causeId "
                        + "AND Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId "
                        + "AND HCoq.dbHazardConsequencePK.consequenceId = Coq.consequenceId "
                        + "AND Haz.hazardId = HCtl.dbControlHazardPK.hazardId "
                        + "AND HCtl.dbControlHazardPK.controlId = Ctl.controlId "
                        + "AND Haz.hazardId = HSbs.dbHazardSbsPK.hazardId ";
                if (!hazardList.isEmpty() || !sbsList.isEmpty()) {
                    qryWhere = constructWhere(qryWhere, hazardList, sbsList);
                }
                return new validateIdObject(true, qrySelect + qryFrom + qryWhere);
            } else if (rqstType.equals("I")) {
                qryWhere = "WHERE (NOT EXISTS (SELECT 'X' FROM DbHazardCause HCau WHERE Haz.hazardId = HCau.dbHazardCausePK.hazardId) "
                        + "OR NOT EXISTS (SELECT 'X' FROM DbHazardConsequence HCoq WHERE Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId) "
                        + "OR NOT EXISTS (SELECT 'X' FROM DbControlHazard HCtl WHERE Haz.hazardId = HCtl.dbControlHazardPK.hazardId)) ";
                if (!hazardList.isEmpty() || !sbsList.isEmpty()) {
                    twoStringObjs tmpResponse = constructFrom(qryFrom, qryWhere, hazardList, sbsList);
                    qryFrom = tmpResponse.fromField;
                    qryWhere = tmpResponse.whereField;
                    qryWhere = constructWhere(qryWhere, hazardList, sbsList);
                }
                return new validateIdObject(true, qrySelect + qryFrom + qryWhere);
            }
            return new validateIdObject(false, "ejb.DbHazardFacade.createQueryString(): "
                    + "The requested hazard type does not match with complete or incomplete.");
        }
        return new validateIdObject(false, "ejb.DbHazardFacade.createQueryString(): "
                + "Some entites could not be matched with the managed by this function.");
    }

    //Performs the logic to build the dynamic FROM AND WHERE JOIN TABLES SQL statements.
    private twoStringObjs constructFrom(String qryFrom, String qryWhere, List<searchObject> hazardList, List<treeNodeObject> sbsList) {
        twoStringObjs responseObj = new twoStringObjs();
        StringBuilder fromString = new StringBuilder(qryFrom);
        StringBuilder whereString = new StringBuilder(qryWhere);
        String initialOper = "";
        boolean causeAdded = false;
        boolean conqsAdded = false;
        boolean controlAdded = false;

        if (!hazardList.isEmpty()) {
            for (int x = 0; x < hazardList.size(); x++) {
                if (whereString.length() == 0) {
                    initialOper = "WHERE";
                } else {
                    initialOper = "AND";
                }
                switch (hazardList.get(x).getEntity1Name()) {
                    case "DbCause":
                    case "DbHazardCause":
                        if (!causeAdded) {
                            causeAdded = true;
                            fromString.append(",DbHazardCause HCau ,DbCause Cau ");
                            whereString.append(initialOper);
                            whereString.append(" Haz.hazardId = HCau.dbHazardCausePK.hazardId AND HCau.dbHazardCausePK.causeId = Cau.causeId ");
                        }
                        break;
                    case "DbConsequence":
                    case "DbHazardConsequence":
                        if (!conqsAdded) {
                            conqsAdded = true;
                            fromString.append(",DbHazardConsequence HCoq ,DbConsequence Coq ");
                            whereString.append(initialOper);
                            whereString.append(" Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId AND HCoq.dbHazardConsequencePK.consequenceId = Coq.consequenceId ");
                        }
                        break;
                    case "DbControl":
                    case "DbControlHazard":
                        if (!controlAdded) {
                            controlAdded = true;
                            fromString.append(",DbControlHazard HCtl ,DbControl Ctl ");
                            whereString.append(initialOper);
                            whereString.append(" Haz.hazardId = HCtl.dbControlHazardPK.hazardId AND HCtl.dbControlHazardPK.controlId = Ctl.controlId ");
                        }
                        break;
                }
            }
        }

        if (!sbsList.isEmpty()) {
            fromString.append(",DbHazardSbs HSbs ");
            if (whereString.length() == 0) {
                initialOper = "WHERE";
            } else {
                initialOper = "AND";
            }
            whereString.append(initialOper);
            whereString.append(" Haz.hazardId = HSbs.dbHazardSbsPK.hazardId ");
        }

        responseObj.fromField = fromString.toString();
        responseObj.whereField = whereString.toString();

        return responseObj;
    }

    //Performs the logic to create the WHERE SQL criteria
    private String constructWhere(String qryWhere, List<searchObject> hazardList, List<treeNodeObject> sbsList) {
        int curVariableParam = 0;
        StringBuilder whereFields = new StringBuilder(qryWhere);

        if (!hazardList.isEmpty()) {
            for (int x = 0; x < hazardList.size(); x++) {
                StringBuilder tmpString = new StringBuilder();
                curVariableParam++;
                if (whereFields.length() == 0) {
                    tmpString.append("WHERE ");
                } else {
                    tmpString.append("AND ");
                }
                if (hazardList.get(x).getEntity3Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity3Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    whereFields.append(tmpString.toString());
                } else if (hazardList.get(x).getEntity2Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    whereFields.append(tmpString.toString());
                } else if (hazardList.get(x).getEntity1Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    whereFields.append(tmpString.toString());
                }

            }
        }

        if (!sbsList.isEmpty()) {
            StringBuilder tmpString = new StringBuilder();
            if (whereFields.length() == 0) {
                tmpString.append("WHERE ");
            } else {
                tmpString.append("AND ");
            }
            tmpString.append("HSbs.dbHazardSbsPK.sbsId");
            tmpString.append(logicOperator("in", curVariableParam));
            whereFields.append(tmpString.toString());
        }

        return whereFields.toString();
    }

    //Create the where line according to the searched type
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

    //Returns the select statament for extended view or normal view.
    private String getSelectBySearchType(String rqstType) {
        switch (rqstType) {
            case "Normal":
                return "SELECT DISTINCT Haz ";
            case "DefaultView":
                return "SELECT DISTINCT Haz, (SELECT SUM(Qlt.rating * Qlt.weighting) FROM DbQuality Qlt WHERE Qlt.dbQualityPK.hazardId = Haz.hazardId), "
                        + "(SELECT SUM(Qlt.weighting) FROM DbQuality Qlt WHERE Qlt.dbQualityPK.hazardId = Haz.hazardId), "
                        + "(SELECT COUNT(Qlt.weighting) FROM DbQuality Qlt WHERE Qlt.dbQualityPK.hazardId = Haz.hazardId), "
                        + "(SELECT 'true' FROM DbHazard Haz_1 WHERE (NOT EXISTS (SELECT 'X' FROM DbHazardCause HCau WHERE Haz_1.hazardId = HCau.dbHazardCausePK.hazardId) "
                        + "OR NOT EXISTS (SELECT 'X' FROM DbHazardConsequence HCoq WHERE Haz_1.hazardId = HCoq.dbHazardConsequencePK.hazardId) "
                        + "OR NOT EXISTS (SELECT 'X' FROM DbControlHazard HCtl WHERE Haz_1.hazardId = HCtl.dbControlHazardPK.hazardId)) "
                        + "AND Haz_1.hazardId = Haz.hazardId) ";
            default:
                return null;
        }
    }

    @Override
    public void wfApproveHazard(String hazardId, String finalDecision) {
        try {
            DbHazard tmpHazard = this.find(hazardId);
            if (tmpHazard != null) {
                DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
                if (finalDecision.equals("Approved")) {
                    tmpHazard.setHazardSystemStatus(new DbhazardSystemStatus(2));
                    tmpHazard.setUpdatedDateTime(new java.util.Date());
                    tmpHazard.setUserIdUpdate(activeUser.getUserId());
                } else if (finalDecision.equals("Rejected")) {
                    tmpHazard.setHazardSystemStatus(new DbhazardSystemStatus(3));
                    tmpHazard.setUpdatedDateTime(new java.util.Date());
                    tmpHazard.setUserIdUpdate(activeUser.getUserId());
                }
                this.edit(tmpHazard);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void wfDeleteHazard(String hazardId) {
        try {
            DbHazard tmpHazard = this.find(hazardId);
            if (tmpHazard != null) {
                tmpHazard.setHazardSystemStatus(new DbhazardSystemStatus(4));
                tmpHazard.setUpdatedDateTime(new java.util.Date());
                this.edit(tmpHazard);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Override
    public List<Object[]> exportHazards(List<String> searchedHazards) {
        String querySTR;
        List<Object[]> resultList = new ArrayList<>();
        try {
            querySTR = "SELECT (SELECT COUNT(*) FROM db_control_hazard CtlHaz WHERE CtlHaz.hazardId = Haz.hazardId AND CtlHaz.controlExistingOrProposed = 'E'), "
                        + "(SELECT COUNT(*) FROM db_control_hazard CtlHaz WHERE CtlHaz.hazardId = Haz.hazardId AND CtlHaz.controlExistingOrProposed = 'P'), "
                        + "Haz.hazardId, "
                        + "Loc.locationName, "
                        + "Ctx.hazardContextName, "
                        + "(SELECT GROUP_CONCAT('(-) ', Cau.causeDescription ORDER BY Cau.causeId SEPARATOR '\n') FROM ssd.db_hazard_cause HCau, ssd.db_cause Cau "
                            + "WHERE HCau.causeId = Cau.causeId AND HCau.hazardId = Haz.hazardId GROUP BY HCau.hazardId), "
                        + "Haz.hazardDescription, "
                        + "Own.ownerName, "
                        + "(SELECT GROUP_CONCAT('(-) ', Coq.consequenceDescription ORDER BY Coq.consequenceId SEPARATOR '\n') FROM ssd.db_hazard_consequence HCoq, ssd.db_consequence Coq "
                            + "WHERE HCoq.consequenceId = Coq.consequenceId AND HCoq.hazardId = Haz.hazardId GROUP BY HCoq.hazardId), "
                        + "Typ.hazardTypeName, "
                        + "CtlExs.controlId as CtlIdExs, "
                        + "CtlExs.controlDescription as CtlDesExs, "
                        + "CtlExsOwn.ownerName as OwnerExs, "
                        + "RCls.riskClassName, "
                        + "RCurSvt.severityScore as CurSev, " 
                        + "RCurFrq.frequencyScore as CurFreq, "
                        + "Haz.riskCurrentScore as CurScr, "
                        + "CtlPro.controlId as CtlIdPro, "
                        + "CtlPro.controlDescription as CtlDesPro, "
                        + "CtlProHir.controlHierarchyName, "
                        + "CtlProOwn.ownerName as OwnerPro, "
                        + "CtlProRmd.controlRecommendName, "
                        + "RTarSvt.severityScore as TarSev, "
                        + "RTarFrq.frequencyScore as TarFrq, "
                        + "Haz.riskTargetScore as TarScr, "
                        + "Haz.hazardComment, "
                        + "Haz.hazardDate, "
                        + "Haz.hazardWorkshop, "
                        + "HazSts.hazardStatusName, "
                        + "Haz.hazardReview, "
                        + "(SELECT GROUP_CONCAT('', Fle.fileName ORDER BY Fle.fileId SEPARATOR ', ') FROM db_hazard_files HFle, db_files Fle "
                            + "WHERE HFle.fileId = Fle.fileId AND HFle.hazardId = Haz.hazardId GROUP BY HFle.hazardId) "
                     + "FROM db_hazard Haz "
                        + "INNER JOIN db_location Loc "
                            + "ON Haz.hazardLocation = Loc.locationId "
                        + "INNER JOIN db_hazardContext Ctx "
                            + "ON Haz.hazardContextId = Ctx.hazardContextId "
                        + "INNER JOIN db_owners Own "
                            + "ON Haz.ownerId = Own.ownerId "
                        + "INNER JOIN db_hazardType Typ "
                            + "ON Haz.hazardTypeId = Typ.hazardTypeId "
                        + "LEFT JOIN db_control_hazard CtlHazExs "
                            + "ON Haz.hazardId = CtlHazExs.hazardId "
                            + "AND CtlHazExs.controlExistingOrProposed = 'E' "
                        + "LEFT JOIN db_control CtlExs "
                            + "ON CtlHazExs.controlId = CtlExs.controlId "
                        + "LEFT JOIN db_owners CtlExsOwn "
                            + "ON CtlExs.ownerId = CtlExsOwn.ownerId "
                        + "INNER JOIN db_riskClass RCls "
                            + "ON Haz.riskClassId = RCls.riskClassId "
                        + "INNER JOIN db_riskSeverity RTarSvt "
                            + "ON Haz.riskTargetSeverityId = RTarSvt.riskSeverityId "
                        + "INNER JOIN db_riskFrequency RTarFrq "
                            + "ON Haz.riskTargetFrequencyId = RTarFrq.riskFrequencyId "
                        + "INNER JOIN ssd.db_riskSeverity RCurSvt " 
                            + "ON Haz.riskCurrentSeverityId = RCurSvt.riskSeverityId " 
                        + "INNER JOIN ssd.db_riskFrequency RCurFrq " 
                            + "ON Haz.riskCurrentFrequencyId = RCurFrq.riskFrequencyId "
                        + "LEFT JOIN db_control_hazard CtlHazPro "
                            + "ON Haz.hazardId = CtlHazPro.hazardId "
                            + "AND CtlHazPro.controlExistingOrProposed = 'P' "
                        + "LEFT JOIN db_control CtlPro "
                            + "ON CtlHazPro.controlId = CtlPro.controlId "
                        + "LEFT JOIN db_controlRecommend CtlProRmd "
                            + "ON CtlHazPro.controlRecommendId = CtlProRmd.controlRecommendId "
                        + "LEFT JOIN db_owners CtlProOwn "
                            + "ON CtlPro.ownerId = CtlProOwn.ownerId "
                        + "LEFT JOIN db_controlHierarchy CtlProHir "
                            + "ON CtlPro.controlHierarchyId = CtlProHir.controlHierarchyId "
                        + "INNER JOIN db_hazardStatus HazSts "
                            + "ON Haz.hazardStatusId = HazSts.hazardStatusId "
                     + "WHERE Haz.hazardId IN ?1 "
                     + "ORDER BY 3";
            Query query = em.createNativeQuery(querySTR);
            query.setParameter(1, searchedHazards);

            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }

    class twoStringObjs {

        public String fromField;
        public String whereField;
    }
}
