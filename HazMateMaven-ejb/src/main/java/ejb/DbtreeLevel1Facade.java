/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel1;
import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
import entities.DbtreeLevel4;
import entities.DbtreeLevel5;
import entities.DbtreeLevel6;
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
public class DbtreeLevel1Facade extends AbstractFacade<DbtreeLevel1> implements DbtreeLevel1FacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbtreeLevel1Facade() {
        super(DbtreeLevel1.class);
    }

    @Override
    public DbtreeLevel1 findByName(String nodeName) {
        DbtreeLevel1 tmpNode = new DbtreeLevel1();
        List<DbtreeLevel1> resultList = new ArrayList<>();
        String querySTR;

        try {
            querySTR = "FROM DbtreeLevel1 lv1 WHERE lv1.treeLevel1Name = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, nodeName);
            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                tmpNode = resultList.get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return tmpNode;
    }

    @Override
    public DbtreeLevel1 findByIndex(int indexLv1) {
        DbtreeLevel1 tmpLevel1 = new DbtreeLevel1();
        List<DbtreeLevel1> resultList = new ArrayList<>();
        String querySTR;

        try {
            querySTR = "FROM DbtreeLevel1 lv1 WHERE lv1.treeLevel1Index = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, indexLv1);
            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                tmpLevel1 = resultList.get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return tmpLevel1;
    }

    @Override
    public DbtreeLevel2 findByIndex(int indexLv1, int indexLv2) {
        DbtreeLevel2 tmpLevel2 = new DbtreeLevel2();
        List<DbtreeLevel2> resultList = new ArrayList<>();
        String querySTR;

        try {
            querySTR = "SELECT Lv2 FROM DbtreeLevel1 Lv1, DbtreeLevel2 Lv2 "
                    + "WHERE Lv1.treeLevel1Index = ?1 "
                    + "AND Lv1.treeLevel1Id = Lv2.dbtreeLevel1.treeLevel1Id "
                    + "AND Lv2.treeLevel2Index = ?2 ";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, indexLv1);
            query.setParameter(2, indexLv2);
            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                tmpLevel2 = resultList.get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return tmpLevel2;
    }

    @Override
    public DbtreeLevel3 findByIndex(int indexLv1, int indexLv2, int indexLv3) {
        DbtreeLevel3 tmpLevel3 = new DbtreeLevel3();
        List<DbtreeLevel3> resultList = new ArrayList<>();
        String querySTR;

        try {
            querySTR = "SELECT Lv3 FROM DbtreeLevel1 Lv1, DbtreeLevel2 Lv2, DbtreeLevel3 Lv3 "
                    + "WHERE Lv1.treeLevel1Index = ?1 "
                    + "AND Lv1.treeLevel1Id = Lv2.dbtreeLevel1.treeLevel1Id "
                    + "AND Lv2.treeLevel2Index = ?2 "
                    + "AND Lv2.dbtreeLevel2PK.treeLevel1Id = Lv3.dbtreeLevel3PK.treeLevel1Id "
                    + "AND Lv2.dbtreeLevel2PK.treeLevel2Id = Lv3.dbtreeLevel3PK.treeLevel2Id "
                    + "AND Lv3.treeLevel3Index = ?3";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, indexLv1);
            query.setParameter(2, indexLv2);
            query.setParameter(3, indexLv3);
            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                tmpLevel3 = resultList.get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return tmpLevel3;
    }

    @Override
    public DbtreeLevel4 findByIndex(int indexLv1, int indexLv2, int indexLv3, int indexLv4) {
        DbtreeLevel4 tmpLevel4 = new DbtreeLevel4();
        List<DbtreeLevel4> resultList = new ArrayList<>();
        String querySTR;

        try {
            querySTR = "SELECT Lv4 "
                    + "FROM DbtreeLevel1 Lv1, DbtreeLevel2 Lv2, DbtreeLevel3 Lv3, DbtreeLevel4 Lv4 "
                    + "WHERE Lv1.treeLevel1Index = ?1 "
                    + "AND Lv1.treeLevel1Id = Lv2.dbtreeLevel1.treeLevel1Id "
                    + "AND Lv2.treeLevel2Index = ?2 "
                    + "AND Lv2.dbtreeLevel2PK.treeLevel1Id = Lv3.dbtreeLevel3PK.treeLevel1Id "
                    + "AND Lv2.dbtreeLevel2PK.treeLevel2Id = Lv3.dbtreeLevel3PK.treeLevel2Id "
                    + "AND Lv3.treeLevel3Index = ?3 "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel1Id = Lv4.dbtreeLevel4PK.treeLevel1Id "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel2Id = Lv4.dbtreeLevel4PK.treeLevel2Id "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel3Id = Lv4.dbtreeLevel4PK.treeLevel3Id "
                    + "AND Lv4.treeLevel4Index = ?4";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, indexLv1);
            query.setParameter(2, indexLv2);
            query.setParameter(3, indexLv3);
            query.setParameter(4, indexLv4);
            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                tmpLevel4 = resultList.get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return tmpLevel4;
    }

    @Override
    public DbtreeLevel5 findByIndex(int indexLv1, int indexLv2, int indexLv3, int indexLv4, int indexLv5) {
        DbtreeLevel5 tmpLevel5 = new DbtreeLevel5();
        List<DbtreeLevel5> resultList = new ArrayList<>();
        String querySTR;

        try {
            querySTR = "SELECT Lv5 "
                    + "FROM DbtreeLevel1 Lv1, DbtreeLevel2 Lv2, DbtreeLevel3 Lv3, DbtreeLevel4 Lv4, DbtreeLevel5 Lv5 "
                    + "WHERE Lv1.treeLevel1Index = ?1 "
                    + "AND Lv1.treeLevel1Id = Lv2.dbtreeLevel1.treeLevel1Id "
                    + "AND Lv2.treeLevel2Index = ?2 "
                    + "AND Lv2.dbtreeLevel2PK.treeLevel1Id = Lv3.dbtreeLevel3PK.treeLevel1Id "
                    + "AND Lv2.dbtreeLevel2PK.treeLevel2Id = Lv3.dbtreeLevel3PK.treeLevel2Id "
                    + "AND Lv3.treeLevel3Index = ?3 "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel1Id = Lv4.dbtreeLevel4PK.treeLevel1Id "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel2Id = Lv4.dbtreeLevel4PK.treeLevel2Id "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel3Id = Lv4.dbtreeLevel4PK.treeLevel3Id "
                    + "AND Lv4.treeLevel4Index = ?4 "
                    + "AND Lv4.dbtreeLevel4PK.treeLevel1Id = Lv5.dbtreeLevel5PK.treeLevel1Id "
                    + "AND Lv4.dbtreeLevel4PK.treeLevel2Id = Lv5.dbtreeLevel5PK.treeLevel2Id "
                    + "AND Lv4.dbtreeLevel4PK.treeLevel3Id = Lv5.dbtreeLevel5PK.treeLevel3Id "
                    + "AND Lv4.dbtreeLevel4PK.treeLevel4Id = Lv5.dbtreeLevel5PK.treeLevel4Id "
                    + "AND Lv5.treeLevel5Index = ?5";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, indexLv1);
            query.setParameter(2, indexLv2);
            query.setParameter(3, indexLv3);
            query.setParameter(4, indexLv4);
            query.setParameter(5, indexLv5);
            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                tmpLevel5 = resultList.get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return tmpLevel5;
    }

    @Override
    public DbtreeLevel6 findByIndex(int indexLv1, int indexLv2, int indexLv3, int indexLv4, int indexLv5, int indexLv6) {
        DbtreeLevel6 tmpLevel6 = new DbtreeLevel6();
        List<DbtreeLevel6> resultList = new ArrayList<>();
        String querySTR;

        try {
            querySTR = "SELECT Lv6 "
                    + "FROM DbtreeLevel1 Lv1, DbtreeLevel2 Lv2, DbtreeLevel3 Lv3, DbtreeLevel4 Lv4, DbtreeLevel5 Lv5, DbtreeLevel6 Lv6 "
                    + "WHERE Lv1.treeLevel1Index = ?1 "
                    + "AND Lv1.treeLevel1Id = Lv2.dbtreeLevel1.treeLevel1Id "
                    + "AND Lv2.treeLevel2Index = ?2 "
                    + "AND Lv2.dbtreeLevel2PK.treeLevel1Id = Lv3.dbtreeLevel3PK.treeLevel1Id "
                    + "AND Lv2.dbtreeLevel2PK.treeLevel2Id = Lv3.dbtreeLevel3PK.treeLevel2Id "
                    + "AND Lv3.treeLevel3Index = ?3 "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel1Id = Lv4.dbtreeLevel4PK.treeLevel1Id "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel2Id = Lv4.dbtreeLevel4PK.treeLevel2Id "
                    + "AND Lv3.dbtreeLevel3PK.treeLevel3Id = Lv4.dbtreeLevel4PK.treeLevel3Id "
                    + "AND Lv4.treeLevel4Index = ?4 "
                    + "AND Lv4.dbtreeLevel4PK.treeLevel1Id = Lv5.dbtreeLevel5PK.treeLevel1Id "
                    + "AND Lv4.dbtreeLevel4PK.treeLevel2Id = Lv5.dbtreeLevel5PK.treeLevel2Id "
                    + "AND Lv4.dbtreeLevel4PK.treeLevel3Id = Lv5.dbtreeLevel5PK.treeLevel3Id "
                    + "AND Lv4.dbtreeLevel4PK.treeLevel4Id = Lv5.dbtreeLevel5PK.treeLevel4Id "
                    + "AND Lv5.treeLevel5Index = ?5 "
                    + "AND Lv5.dbtreeLevel5PK.treeLevel1Id = Lv6.dbtreeLevel6PK.treeLevel1Id "
                    + "AND Lv5.dbtreeLevel5PK.treeLevel2Id = Lv6.dbtreeLevel6PK.treeLevel2Id "
                    + "AND Lv5.dbtreeLevel5PK.treeLevel3Id = Lv6.dbtreeLevel6PK.treeLevel3Id "
                    + "AND Lv5.dbtreeLevel5PK.treeLevel4Id = Lv6.dbtreeLevel6PK.treeLevel4Id "
                    + "AND Lv5.dbtreeLevel5PK.treeLevel5Id = Lv6.dbtreeLevel6PK.treeLevel5Id "
                    + "AND Lv6.treeLevel6Index = ?6";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, indexLv1);
            query.setParameter(2, indexLv2);
            query.setParameter(3, indexLv3);
            query.setParameter(4, indexLv4);
            query.setParameter(5, indexLv5);
            query.setParameter(6, indexLv6);
            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                tmpLevel6 = resultList.get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return tmpLevel6;
    }

}
