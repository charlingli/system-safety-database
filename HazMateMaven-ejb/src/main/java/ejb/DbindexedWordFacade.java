/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbindexedWord;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Stateless
public class DbindexedWordFacade extends AbstractFacade<DbindexedWord> implements DbindexedWordFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbindexedWordFacade() {
        super(DbindexedWord.class);
    }

    @Override
    public List<Object[]> findSimilarities(String objectType, List<String> listOfValues) {
        String querySTR;
        List<Object[]> resultList = new ArrayList<>();
        String QueryOrSTR = "";

        if (listOfValues.size() == 1) {
            QueryOrSTR += "AND (idW.indexedWord = '" + listOfValues.get(0) + "') ";
        } else {
            for (String tmp : listOfValues) {

                if (tmp.equals(listOfValues.get(0))) {
                    QueryOrSTR += "AND (idW.indexedWord = '" + tmp + "' ";
                    continue;
                }
                if (tmp.equals(listOfValues.get(listOfValues.size() - 1))) {
                    QueryOrSTR += "OR idW.indexedWord = '" + tmp + "') ";
                    break;
                }
                QueryOrSTR += "OR idW.indexedWord = '" + tmp + "' ";
            }
        }
        try {
            querySTR = "SELECT idW.objectId, "
                    + "COUNT(idW.indexedWord), "
                    + "(SELECT COUNT(*) FROM db_indexedWord idW_aE WHERE idW_aE.objectId = idW.objectId AND idW_aE.objectType = idW.objectType), "
                    + "GROUP_CONCAT(idW.indexedWord ORDER BY idW.objectLineNo SEPARATOR ',') "
                    + "FROM db_indexedWord idW "
                    + "WHERE idW.objectType = ?1 "
                    + QueryOrSTR
                    + "GROUP BY idW.objectId, idW.objectType";
            Query query = em.createNativeQuery(querySTR);
            query.setParameter(1, objectType);
            resultList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }

    @Override
    public int truncateTable() {
        try {
            String query = "TRUNCATE TABLE db_indexedWord";
            return em.createNativeQuery(query).executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

}
