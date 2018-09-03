/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbwfHeader;
import entities.DbwfLine;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author David Ortega
 */
@Local
public interface DbwfLineFacadeLocal {

    void create(DbwfLine dbwfLine);

    void edit(DbwfLine dbwfLine);

    void remove(DbwfLine dbwfLine);

    DbwfLine find(Object id);

    List<DbwfLine> findAll();

    List<DbwfLine> findRange(int[] range);

    int count();
    
    List<DbwfLine> findAllOfWF(String wfId);
    
    DbwfLine findByIdAndUser(DbwfLine wfObjLn, int userId);
    
    boolean validateAllApprovers(DbwfHeader wfObj, String decisionType);
    
    boolean validate50plus1Approvers(DbwfHeader wfObj, String decisionType);
    
    boolean validateFirstApprover(DbwfHeader wfObj, String decisionType);

}
