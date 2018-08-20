/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.validateIdObject;
import entities.DbglobalId;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbglobalIdFacade extends AbstractFacade<DbglobalId> implements DbglobalIdFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbglobalIdFacade() {
        super(DbglobalId.class);
    }

    @Override
    public validateIdObject nextConsecutive(String key1, String separator, int zeroPositions) {
        validateIdObject tmpValidate = validateFields(1, separator, key1, "", "");
        if (tmpValidate.isValidationFlag()) {
            DbglobalId bridgeGlobalId = this.findByKey(key1);
            if (bridgeGlobalId.getGlobalIdConsecutive() == null) {
                return createCombination(1, separator, key1, null, null, zeroPositions);
            } else {
                return updateCombination(bridgeGlobalId, 1, separator, key1, "", "", zeroPositions);
            }
        }
        return tmpValidate;
    }

    @Override
    public validateIdObject nextConsecutive(String key1, String key2, String separator, int zeroPositions) {
        validateIdObject tmpValidate = validateFields(2, separator, key1, key2, "");
        if (tmpValidate.isValidationFlag()) {
            DbglobalId bridgeGlobalId = this.findByKey(key1, key2);
            if (bridgeGlobalId.getGlobalIdConsecutive() == null) {
                return createCombination(2, separator, key1, key2, "", zeroPositions);
            } else {
                return updateCombination(bridgeGlobalId, 2, separator, key1, key2, "", zeroPositions);
            }
        }
        return tmpValidate;
    }

    @Override
    public validateIdObject nextConsecutive(String key1, String key2, String key3, String separator, int zeroPositions) {
        validateIdObject tmpValidate = validateFields(3, separator, key1, key2, key3);
        if (tmpValidate.isValidationFlag()) {
            DbglobalId bridgeGlobalId = this.findByKey(key1, key2, key3);
            if (bridgeGlobalId.getGlobalIdConsecutive() == null) {
                return createCombination(3, separator, key1, key2, key3, zeroPositions);
            } else {
                return updateCombination(bridgeGlobalId, 3, separator, key1, key2, key3, zeroPositions);
            }
        }
        return tmpValidate;
    }

    @Override
    public DbglobalId findByKey(String key1) {
        String queryStr;
        List<DbglobalId> resultList = new ArrayList<>();
        DbglobalId resultGlobalId = new DbglobalId();

        try {
            queryStr = "FROM DbglobalId gId WHERE gId.key1 = ?1 AND gId.key2 = null AND gId.key3 = null";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, key1);

            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                resultGlobalId = resultList.get(0);
            }

        } catch (Exception e) {

            throw e;
        }
        return resultGlobalId;
    }

    @Override
    public DbglobalId findByKey(String key1, String key2) {
        String queryStr;
        List<DbglobalId> resultList = new ArrayList<>();
        DbglobalId resultGlobalId = new DbglobalId();

        try {
            queryStr = "FROM DbglobalId gId WHERE gId.key1 = ?1 AND gId.key2 = ?2 AND gId.key3 = null";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, key1);
            query.setParameter(2, key2);

            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                resultGlobalId = resultList.get(0);
            }

        } catch (Exception e) {

            throw e;
        }
        return resultGlobalId;
    }

    @Override
    public DbglobalId findByKey(String key1, String key2, String key3) {
        String queryStr;
        List<DbglobalId> resultList = new ArrayList<>();
        DbglobalId resultGlobalId = new DbglobalId();

        try {
            queryStr = "FROM DbglobalId gId WHERE gId.key1 = ?1 AND gId.key2 = ?2 AND gId.key3 = ?3 ";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, key1);
            query.setParameter(2, key2);
            query.setParameter(3, key3);

            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                resultGlobalId = resultList.get(0);
            }

        } catch (Exception e) {

            throw e;
        }
        return resultGlobalId;
    }

    private validateIdObject validateFields(int paramNo, String separator, String key1, String key2, String key3) {
        if (!"".equals(separator) && separator.length() > 1) {
            return new validateIdObject(false, "Error: Separator must have one character.");
        } else if (paramNo == 1) {
            if ("".equals(key1)) {
                return new validateIdObject(false, "Error: Key1 must be different to empty.");
            }
        } else if (paramNo == 2) {
            if ("".equals(key1) || "".equals(key2)) {
                return new validateIdObject(false, "Error: Key1 and key2 must be different to empty.");
            }
        } else if (paramNo == 3) {
            if ("".equals(key1) || "".equals(key2) || "".equals(key3)) {
                return new validateIdObject(false, "Error: Key1, key2 and key3 must be different to empty.");
            }
        } else if (paramNo > 3) {
            return new validateIdObject(false, "Error: This method does not control more than 3 parameters.");
        }
        return new validateIdObject(true, "");
    }

    private validateIdObject createCombination(int paramNo, String separator, String key1, String key2, String key3, int zeroPositions) {
        DbglobalId tmpGlobalId = null;
        String tmpResult;

        switch (paramNo) {
            case 1:
                tmpGlobalId = new DbglobalId(key1, 2);
                break;
            case 2:
                tmpGlobalId = new DbglobalId(key1, key2, 2);
                break;
            case 3:
                tmpGlobalId = new DbglobalId(key1, key2, key3, 2);
                break;
        }

        this.create(tmpGlobalId);
        tmpResult = createIdString(paramNo, separator, key1, key2, key3, zeroPositions, 1);
        if (tmpResult != "") {
            return new validateIdObject(true, tmpResult);
        }

        return new validateIdObject(false, "Unkown exception in createCombination method.");
    }

    private validateIdObject updateCombination(DbglobalId curGlobal, int paramNo, String separator, String key1, String key2, String key3, int zeroPositions) {
        int currentConsecutive = curGlobal.getCurrentNumber();
        curGlobal.setCurrentNumber(currentConsecutive + 1);
        this.edit(curGlobal);
        String tmpResult = createIdString(paramNo, separator, key1, key2, key3, zeroPositions, currentConsecutive);
        if (tmpResult != "") {
            return new validateIdObject(true, tmpResult);
        }

        return new validateIdObject(false, "Unkown exception in updateCombination method.");
    }

    private String createIdString(int paramNo, String separator, String key1, String key2, String key3, int zeroPositions, int consecutive) {
        StringBuilder tmpString = new StringBuilder();
        String numberAsString = "";

        switch (paramNo) {
            case 1:
                tmpString.append(key1);
                tmpString.append(separator);
                numberAsString = String.format("%0" + zeroPositions + "d", consecutive);
                tmpString.append(numberAsString);
                break;
            case 2:
                tmpString.append(key1);
                tmpString.append(separator);
                tmpString.append(key2);
                tmpString.append(separator);
                numberAsString = String.format("%0" + zeroPositions + "d", consecutive);
                tmpString.append(numberAsString);
                break;
            case 3:
                tmpString.append(key1);
                tmpString.append(separator);
                tmpString.append(key2);
                tmpString.append(separator);
                tmpString.append(key3);
                tmpString.append(separator);
                numberAsString = String.format("%0" + zeroPositions + "d", consecutive);
                tmpString.append(numberAsString);
                break;
        }
        return tmpString.toString();
    }

}
