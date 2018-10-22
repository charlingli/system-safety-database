/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbCause;
import entities.DbConsequence;
import entities.DbControl;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import entities.DbHazardSbs;
import customObjects.searchObject;
import customObjects.treeNodeObject;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbHazardFacadeLocal {

    void create(DbHazard dbHazard);

    void edit(DbHazard dbHazard);

    void remove(DbHazard dbHazard);

    DbHazard find(Object id);

    List<DbHazard> findAll();

    List<DbHazard> findRange(int[] range);
    
    int count();

    List<DbControlHazard> getControlHazard(String hazardId);
    
    List<DbControlHazard> getMControlHazard(String hazardId);
    
    List<DbControlHazard> getPControlHazard(String hazardId);

    List<DbControl> getControls(int controlId);

    List<DbConsequence> getConsequences(String hazardId);

    List<DbHazardSbs> getSbs(String hazardId);

    List<DbCause> getCauses(String hazardId);

    List<DbHazardCause> getHazardCause(String hazardId);

    List<DbHazardConsequence> getHazardConsequence(String hazardId);

    List<DbHazard> findByName(String fieldName, String fieldValue);

//    List<DbHazard> findAllHazards();
//
//    List<DbHazard> findHazardsByFields(List<searchObject> hazardList);
//
//    List<DbHazard> findHazardsByFieldsAndSbs(List<searchObject> hazardList, List<treeNodeObject> sbsList);
//
//    List<DbHazard> findHazardsBySbs(List<treeNodeObject> sbsList);
//    
    List<DbHazard> findHazardsByFieldsOnly(List<searchObject> hazardList);
    
    List<DbHazard> validateHazardId(String hazardId);
    
    int getRiskScore(int frequencyScore, int severityScore);
    
    List<DbHazard> getHazardsFromCause(int causeId);
    
    List<DbHazard> getHazardsFromConsequence(int consequenceId);
    
    List<DbHazard> getHazardsFromControl(int controlId);
    
    List<Object> findHazards(List<searchObject> hazardList, List<treeNodeObject> sbsList, String rqstType, String srchType);
    
    void wfApproveHazard(String hazardId, String finalDecision);
    
    void wfDeleteHazard(String hazardId);
    
}
