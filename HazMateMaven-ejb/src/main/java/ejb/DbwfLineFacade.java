/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbwfHeader;
import entities.DbwfLine;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author David Ortega
 */
@Stateless
public class DbwfLineFacade extends AbstractFacade<DbwfLine> implements DbwfLineFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbwfLineFacade() {
        super(DbwfLine.class);
    }
    
    @Override
    public DbwfLine findByIdAndUser(DbwfLine wfObjLn, int userId) {
        String queryStr;
        List<DbwfLine> resultantList = new ArrayList<>();
        DbwfLine resultLine = new DbwfLine();

        try {
            queryStr = "FROM DbwfLine l WHERE l.dbwfLinePK.wfId = ?1 AND l.wfUserIdApprover.userId = ?2";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, wfObjLn.getDbwfLinePK().getWfId());
            query.setParameter(2, userId);

            resultantList = query.getResultList();

            if (!resultantList.isEmpty()) {
                resultLine = resultantList.get(0);
            }

        } catch (Exception e) {

            throw e;
        }
        return resultLine;       
    }
    
    @Override
    public List<DbwfLine> findOpenByUser(int userId) {
        String queryStr;
        List<DbwfLine> resultantList = new ArrayList<>();

        try {
            queryStr = "SELECT l FROM DbwfLine l WHERE l.wfUserIdApprover.userId = ?1 " + 
                    " AND EXISTS (SELECT 'X' FROM DbwfHeader h WHERE h.wfId = l.dbwfLinePK.wfId " + 
                    " AND h.wfStatus ='O') AND l.wfApproverDecisionId IS NULL";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, userId);

            resultantList = query.getResultList();

        } catch (Exception e) {

            throw e;
        }
        return resultantList;       
    }

    @Override
    //This method checks if all the approvers have given their response to complete the transaction.
    public boolean validateAllApprovers(DbwfHeader wfObj, String decisionType) {
        int numberOfApprovers = 0;
        int numberOfLinesApproved = 0;

        String querySTR;
        try {
            querySTR = "SELECT L FROM DbwfLine L WHERE L.dbwfLinePK.wfId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, wfObj.getWfId());
            numberOfApprovers = query.getResultList().size();
        } catch (Exception e) {
            throw e;
        }

        try {
            querySTR = "SELECT L FROM DbwfLine L WHERE L.dbwfLinePK.wfId = ?1 AND L.wfApproverDecisionId.wfDecisionId = ?2";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, wfObj.getWfId());
            query.setParameter(2, decisionType);
            numberOfLinesApproved = query.getResultList().size();
        } catch (Exception e) {
            throw e;
        }
        return numberOfApprovers != 0 && numberOfApprovers == numberOfLinesApproved;
    }

    @Override
    //This method checks if the 50% + 1 of the approvers have given their response to complete the transaction.
    public boolean validate50plus1Approvers(DbwfHeader wfObj, String decisionType) {
        int numberOfApprovers = 0;
        int numberOfLinesApproved = 0;

        String querySTR;
        try {
            querySTR = "SELECT L FROM DbwfLine L WHERE L.dbwfLinePK.wfId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, wfObj.getWfId());
            numberOfApprovers = query.getResultList().size();
        } catch (Exception e) {
            throw e;
        }

        try {
            querySTR = "SELECT L FROM DbwfLine L WHERE L.dbwfLinePK.wfId = ?1 AND L.wfApproverDecisionId.wfDecisionId = ?2";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, wfObj.getWfId());
            query.setParameter(2, decisionType);
            numberOfLinesApproved = query.getResultList().size();
        } catch (Exception e) {
            throw e;
        }

        //Validate if the 50% of the approvers have already approved the transaction
        if (numberOfApprovers != 0) {
            if (numberOfApprovers == numberOfLinesApproved) {
                return true;
            } else if (numberOfApprovers >= 3) {
                int fithtyAppr = (int) Math.floor(numberOfApprovers / 2);
                if (numberOfLinesApproved >= fithtyAppr + 1) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    //This method checks if at least one of the approvers have answered to complete the transaction.
    public boolean validateFirstApprover(DbwfHeader wfObj, String decisionType) {
        int numberOfLinesApproved = 0;

        String querySTR;
        try {
            querySTR = "SELECT L FROM DbwfLine L WHERE L.dbwfLinePK.wfId = ?1 AND L.wfApproverDecisionId.wfDecisionId = ?2";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, wfObj.getWfId());
            query.setParameter(2, decisionType);
            numberOfLinesApproved = query.getResultList().size();
        } catch (Exception e) {
            throw e;
        }

        return numberOfLinesApproved >= 1;
    }
    
    @Override
    public List<DbwfLine> findAllOfWF(String wfId) {
        return em.createQuery("FROM DbwfLine l WHERE l.dbwfLinePK.wfId = :checkwfId")
                .setParameter("checkwfId", wfId)
                .getResultList();
    }
}
