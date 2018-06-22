/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbLocation;
import entities.DbconstructionType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbconstructionTypeFacadeLocal {

    void create(DbconstructionType dbconstructionType);

    void edit(DbconstructionType dbconstructionType);

    void remove(DbconstructionType dbconstructionType);

    DbconstructionType find(Object id);

    List<DbconstructionType> findAll();

    List<DbconstructionType> findRange(int[] range);

    int count();
    
    List<DbconstructionType> findByName(String fieldName, String fieldValue);
    
    List<DbLocation> checkConstructionType(int constructionTypeId);
    
}
