/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.searchObject;
import customObjects.validateIdObject;
import entities.DbwfHeader;
import entities.DbUser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author David Ortega
 */
@Local
public interface DbwfHeaderFacadeLocal {

    void create(DbwfHeader dbwfHeader);

    void edit(DbwfHeader dbwfHeader);

    void remove(DbwfHeader dbwfHeader);

    DbwfHeader find(Object id);

    List<DbwfHeader> findAll();

    List<DbwfHeader> findRange(int[] range);

    int count();
    
    validateIdObject newWorkFlow(List<DbUser> listApprovers, DbwfHeader wfHeaderObj, String autoNoId);
    
    boolean approvalProcess(DbwfHeader wfHeaderObj, String apprType);
    
    boolean rejectionProcess(DbwfHeader wfHeaderObj, String rjtcdType);
    
    boolean cancellationProcess(DbwfHeader wfHeaderObj);
    
    boolean reviewProcess(DbwfHeader wfHeaderObj, String rwvType);
    
    boolean wfTypesValidation(String wfTypeName);
    
    List<DbwfHeader> findWorkflowsByFieldsOnly(List<searchObject> workflowList);
    
    List<DbwfHeader> findActiveByUser(int userId);
}
