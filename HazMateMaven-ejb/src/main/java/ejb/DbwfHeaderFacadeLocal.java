/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.validateIdObject;
import entities.DbwfHeader;
import entities.DbUser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
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
    
    int approvalProcess(DbwfHeader wfHeaderObj, String apprType);
    
    boolean wfTypesValidation(String wfTypeName);
    
}
