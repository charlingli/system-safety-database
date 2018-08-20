/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.validateIdObject;
import entities.DbUser;
import entities.DbwfHeader;
import entities.DbwfLine;
import entities.DbwfLinePK;
import java.util.List;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lxra
 */
@Stateless
public class DbwfHeaderFacade extends AbstractFacade<DbwfHeader> implements DbwfHeaderFacadeLocal {

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
    //check the return method
    public int approvalProcess(DbwfHeader wfHeaderObj, String apprType) {
        //Because the obj could be just populated with the id field, the function will bring from the db.
        DbwfHeader workObj = this.find(findByName("wfId", wfHeaderObj.getWfId()));
        //if the approval type is Admin Approved the transaction will directly approved.
        if (apprType.equals("adminApproval")){
        
        } else if (apprType.equals("userApproval")) {
        
        }
        //otherwise it will validate if the wf meets the conditions to be approved
        return 0;
    }

    private validateIdObject checkGlobalId(String stringToCheck) {
        String[] parts = stringToCheck.split("-");
        if (parts.length > 3) {
            return new validateIdObject(false, "The consecutive must have less than 3 sections.");
        } else {
            String pattern = "^([A-Za-z0-9]){1,10}$";
            for (String tmp : parts) {
                if (!Pattern.matches(pattern, tmp)) {
                    return new validateIdObject(false, "The consecutive section " + tmp + " is empty or it's lenght is larger than 10 characters.");
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
    
    //This method will validate the particular wf conditions (wf business logic)
    private boolean validateWfConditions(DbwfHeader wfObj){
        
        return false;
    }
    
    //This method will send the approval to the related ejb
    private void wfApproved(DbwfHeader wfObj){
        
    }

    @Override
    public boolean wfTypesValidation(String wfTypeName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
