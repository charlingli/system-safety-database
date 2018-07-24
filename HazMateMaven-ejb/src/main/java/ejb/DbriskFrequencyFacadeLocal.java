/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbriskFrequency;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbriskFrequencyFacadeLocal {

    void create(DbriskFrequency dbriskFrequency);

    void edit(DbriskFrequency dbriskFrequency);

    void remove(DbriskFrequency dbriskFrequency);

    DbriskFrequency find(Object id);

    List<DbriskFrequency> findAll();

    List<DbriskFrequency> findRange(int[] range);

    int count();
    
    List<DbriskFrequency> findByName(String fieldName, String fieldValue);
    
    List<DbHazard> checkRiskFrequency(int riskFrequencyId);
    
    List<DbriskFrequency> getRiskFrequency(int riskFrequencyId);
}
