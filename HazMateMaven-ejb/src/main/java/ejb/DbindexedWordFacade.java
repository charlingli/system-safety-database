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

    @Override
    public void createBatch(List<DbindexedWord> listIndexedWords) {
        int rowsPerLoop = 25000;
        int loops = (int) Math.ceil((double) listIndexedWords.size() / rowsPerLoop);
        for (int i = 0; i < loops; i++) {
            String values = "";
            int ini = i * rowsPerLoop;
            int end;
            if (i == (loops - 1)) {
                end = listIndexedWords.size();
            } else {
                end = (i + 1) * rowsPerLoop;
            }
            for (; ini < end; ini++) {
                values += "('" + listIndexedWords.get(ini).getDbindexedWordPK().getObjectId() + "'," + listIndexedWords.get(ini).getDbindexedWordPK().getObjectLineNo() + ",'"
                        + listIndexedWords.get(ini).getDbindexedWordPK().getObjectType() + "','" + listIndexedWords.get(ini).getIndexedWord() + "'),";
            }
            values = values.substring(0, values.length() - 1);
            String query = "INSERT INTO db_indexedWord (objectId, objectLineNo, objectType, indexedWord) VALUES " + values;
            em.createNativeQuery(query).executeUpdate();
        }
    }
    
    @Override
    public void removeWord(String commonWord) {
        String findQuerySTR = "";
        String deleteQuerySTR = "";
        String updateQuerySTR = "";
        List<DbindexedWord> affectedObjects = new ArrayList<>();
        int deletedRows = 0;
        int updatedRows = 0;
        try {
            findQuerySTR = "FROM DbindexedWord i2 WHERE i2.dbindexedWordPK.objectId "
                    + "IN (SELECT i1.dbindexedWordPK.objectId FROM DbindexedWord i1 "
                    + "WHERE i1.indexedWord = ?1 AND i2.dbindexedWordPK.objectLineNo "
                    + "> i1.dbindexedWordPK.objectLineNo)";
            Query findQuery = em.createQuery(findQuerySTR);
            findQuery.setParameter(1, commonWord);
            affectedObjects = findQuery.getResultList();
            
            deleteQuerySTR = "DELETE FROM DbindexedWord iw WHERE iw.indexedWord = ?1";
            Query deleteQuery = em.createQuery(deleteQuerySTR);
            deleteQuery.setParameter(1, commonWord);
            deletedRows = deleteQuery.executeUpdate();
            
            for (DbindexedWord line : affectedObjects) {
                updateQuerySTR = "UPDATE DbindexedWord iw "
                        + "SET iw.dbindexedWordPK.objectLineNo = "
                        + "iw.dbindexedWordPK.objectLineNo - 1 "
                        + "WHERE iw.dbindexedWordPK.objectId = ?1 "
                        + "AND iw.dbindexedWordPK.objectType = ?2";
                Query updateQuery = em.createQuery(updateQuerySTR);
                updateQuery.setParameter(1, line.getDbindexedWordPK().getObjectId());
                updateQuery.setParameter(2, line.getDbindexedWordPK().getObjectType());
                updatedRows = updateQuery.executeUpdate();
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Override
    public void removeObject(String id, String type) {
        String querySTR;
        int deletedRows;
        try {
            querySTR = "DELETE FROM DbindexedWord iw "
                    + "WHERE iw.dbindexedWordPK.objectId = ?1 "
                    + "AND iw.dbindexedWordPK.objectType = ?2";
            Query deleteQuery = em.createQuery(querySTR);
            deleteQuery.setParameter(1, id);
            deleteQuery.setParameter(2, type);
            deletedRows = deleteQuery.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

}
