/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbriskClass;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbriskClassFacadeLocal {

    void create(DbriskClass dbriskClass);

    void edit(DbriskClass dbriskClass);

    void remove(DbriskClass dbriskClass);

    DbriskClass find(Object id);

    List<DbriskClass> findAll();

    List<DbriskClass> findRange(int[] range);

    int count();
    
    List<DbriskClass> findByName(String fieldName, String fieldValue);
    
    List<DbHazard> checkRiskClass(int riskClassId);
    
}
