/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbriskSeverity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbriskSeverityFacadeLocal {

    void create(DbriskSeverity dbriskSeverity);

    void edit(DbriskSeverity dbriskSeverity);

    void remove(DbriskSeverity dbriskSeverity);

    DbriskSeverity find(Object id);

    List<DbriskSeverity> findAll();

    List<DbriskSeverity> findRange(int[] range);

    int count();
    
    List<DbriskSeverity> findByName(String fieldName, String fieldValue);
    
    List<DbHazard> checkRiskSeverity(int riskSeverityId);
    
    List<DbriskSeverity> getRiskSeverity(int riskSeverityId);
}
