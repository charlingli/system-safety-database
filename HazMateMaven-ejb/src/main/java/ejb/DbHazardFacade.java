/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.searchObject;
import customObjects.treeNodeObject;
import customObjects.validateIdObject;
import entities.DbCause;
import entities.DbConsequence;
import entities.DbControl;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import entities.DbHazardSbs;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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
public class DbHazardFacade extends AbstractFacade<DbHazard> implements DbHazardFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbHazardFacade() {
        super(DbHazard.class);
    }

    @Override
    public List<DbControlHazard> getControlHazard(String hazardId) {
        return em.createQuery("FROM DbControlHazard c WHERE c.dbHazard.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbControlHazard> getMControlHazard(String hazardId) {
        return em.createQuery("FROM DbControlHazard c WHERE c.dbHazard.hazardId = :checkHazardId AND c.controlType = 'M'")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbControlHazard> getPControlHazard(String hazardId) {
        return em.createQuery("FROM DbControlHazard c WHERE c.dbHazard.hazardId = :checkHazardId AND c.controlType = 'P'")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbControl> getControls(int controlId) {
        return em.createQuery("FROM DbControl c WHERE c.dbControl.controlId = :checkControlId")
                .setParameter("checkControlId", controlId)
                .getResultList();
    }

    @Override
    public List<DbConsequence> getConsequences(String hazardId) {
        return em.createQuery("FROM DbConsequence c WHERE c.consequenceHazardId.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbCause> getCauses(String hazardId) {
        return em.createQuery("FROM DbCause c WHERE c.causeHazardId.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbHazardSbs> getSbs(String hazardId) {
        return em.createQuery("FROM DbHazardSbs s WHERE s.dbHazard.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbHazardCause> getHazardCause(String hazardId) {
        return em.createQuery("FROM DbHazardCause c WHERE c.dbHazard.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbHazardConsequence> getHazardConsequence(String hazardId) {
        return em.createQuery("FROM DbHazardConsequence c WHERE c.dbHazard.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public List<DbHazard> findAllHazards() {
        String querySTR;
        List<DbHazard> resultantList = new ArrayList<>();
        try {
            querySTR = "SELECT DISTINCT Haz "
                    + "FROM DbHazard Haz, DbHazardCause HCau, DbHazardConsequence HCoq, DbControlHazard HCtl "
                    + "WHERE Haz.hazardId = HCau.dbHazardCausePK.hazardId "
                    + "AND Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId "
                    + "AND Haz.hazardId = HCtl.dbControlHazardPK.hazardId ";
            Query query = em.createQuery(querySTR);
            resultantList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }

    @Override
    public List<DbHazard> findHazardsByFields(List<searchObject> hazardList) {
        String querySTR = "SELECT DISTINCT Haz "
                + "FROM DbHazard Haz, DbHazardCause HCau, DbHazardConsequence HCoq, DbControlHazard HCtl,"
                + " DbCause Cau, DbConsequence Coq, DbControl Ctl "
                + "WHERE Haz.hazardId = HCau.dbHazardCausePK.hazardId "
                + "AND HCau.dbHazardCausePK.causeId = Cau.causeId "
                + "AND Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId "
                + "AND HCoq.dbHazardConsequencePK.consequenceId = Coq.consequenceId "
                + "AND Haz.hazardId = HCtl.dbControlHazardPK.hazardId "
                + "AND HCtl.dbControlHazardPK.controlId = Ctl.controlId ";
        List<DbHazard> resultantList = new ArrayList<>();
        boolean flagEntities = true;

        //Adding the alias table according to each object in the list
        for (int x = 0; x < hazardList.size(); x++) {
            switch (hazardList.get(x).getEntity1Name()) {
                case "DbHazard":
                    hazardList.get(x).setTableAlias("Haz");
                    break;
                case "DbHazardCause":
                    hazardList.get(x).setTableAlias("HCau");
                    break;
                case "DbHazardConsequence":
                    hazardList.get(x).setTableAlias("HCoq");
                    break;
                case "DbControlHazard":
                    hazardList.get(x).setTableAlias("HCtl");
                    break;
                case "DbCause":
                    hazardList.get(x).setTableAlias("Cau");
                    break;
                case "DbConsequence":
                    hazardList.get(x).setTableAlias("Coq");
                    break;
                case "DbControl":
                    hazardList.get(x).setTableAlias("Ctl");
                    break;
                default:
                    flagEntities = false;
                    break;
            }
        }

        //if some unkown entity was introduced in the list, the function will not go to the next step.
        if (flagEntities) {
            //creating all the where criterias
            for (int x = 0; x < hazardList.size(); x++) {
                StringBuilder tmpString = new StringBuilder();

                tmpString.append("AND ");
                if (hazardList.get(x).getEntity3Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity3Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                } else if (hazardList.get(x).getEntity2Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                } else if (hazardList.get(x).getEntity1Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                }
            }

            try {
                boolean flagParameters = true;
                Query query = em.createQuery(querySTR);
                for (int x = 0; x < hazardList.size(); x++) {
                    int paramNo = x + 1;
                    switch (hazardList.get(x).getFieldType()) {
                        case "string":
                            if ("=".equals(hazardList.get(x).getRelationType())
                                    || "like".equals(hazardList.get(x).getRelationType())) {
                                query.setParameter(paramNo, hazardList.get(x).getUserInput());
                            } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                if (parts.length > 0) {
                                    List<String> inString = new ArrayList<>();
                                    inString.addAll(Arrays.asList(parts));
                                    query.setParameter(paramNo, inString);
                                } else {
                                    flagParameters = false;
                                }
                            }
                            break;
                        case "int":
                            if ("=".equals(hazardList.get(x).getRelationType())
                                    || "like".equals(hazardList.get(x).getRelationType())) {
                                query.setParameter(paramNo, Integer.parseInt(hazardList.get(x).getUserInput()));
                            } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                if (parts.length > 0) {
                                    List<Integer> inInt = new ArrayList<>();
                                    for (String tmpString : parts) {
                                        Integer tmpVal = Integer.parseInt(tmpString);
                                        inInt.add(tmpVal);
                                    }
                                    query.setParameter(paramNo, inInt);
                                } else {
                                    flagParameters = false;
                                }
                            }
                            break;
                        default:
                            flagParameters = false;
                            System.out.println("ejb.DbHazardFacade.findHazardsByFields(): "
                                    + "The fieldType is not allowed.");
                            break;
                    }
                }

                if (flagParameters) {
                    resultantList = query.getResultList();
                } else {
                    System.out.println("ejb.DbHazardFacade.findHazardsByFields(): "
                            + "The function could not find a string separed by commas in a filed marked as IN relationype.");
                }

            } catch (Exception e) {
                throw e;
            }

        } else {
            System.out.println("ejb.DbHazardFacade.findHazardsByFields(): "
                    + "Some entites could not be matched with the managed by this function.");
        }

        return resultantList;
    }

    @Override
    public List<DbHazard> findHazardsByFieldsAndSbs(List<searchObject> hazardList, List<treeNodeObject> sbsList) {
        String querySTR = "SELECT DISTINCT Haz "
                + "FROM DbHazard Haz, DbHazardCause HCau, DbHazardConsequence HCoq, DbControlHazard HCtl,"
                + " DbCause Cau, DbConsequence Coq, DbControl Ctl,  DbHazardSbs HSbs "
                + "WHERE Haz.hazardId = HCau.dbHazardCausePK.hazardId "
                + "AND HCau.dbHazardCausePK.causeId = Cau.causeId "
                + "AND Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId "
                + "AND HCoq.dbHazardConsequencePK.consequenceId = Coq.consequenceId "
                + "AND Haz.hazardId = HCtl.dbControlHazardPK.hazardId "
                + "AND HCtl.dbControlHazardPK.controlId = Ctl.controlId "
                + "AND Haz.hazardId = HSbs.dbHazardSbsPK.hazardId ";
        List<DbHazard> resultantList = new ArrayList<>();
        List<String> inStringTree = new ArrayList<>();
        boolean flagEntities = true;
        boolean flagList = true;

        //Adding the alias table according to each object in the list
        for (int x = 0; x < hazardList.size(); x++) {
            switch (hazardList.get(x).getEntity1Name()) {
                case "DbHazard":
                    hazardList.get(x).setTableAlias("Haz");
                    break;
                case "DbHazardCause":
                    hazardList.get(x).setTableAlias("HCau");
                    break;
                case "DbHazardConsequence":
                    hazardList.get(x).setTableAlias("HCoq");
                    break;
                case "DbControlHazard":
                    hazardList.get(x).setTableAlias("HCtl");
                    break;
                case "DbCause":
                    hazardList.get(x).setTableAlias("Cau");
                    break;
                case "DbConsequence":
                    hazardList.get(x).setTableAlias("Coq");
                    break;
                case "DbControl":
                    hazardList.get(x).setTableAlias("Ctl");
                    break;
                default:
                    flagEntities = false;
                    break;
            }
        }

        //if some unkown entity was introduced in the list, the function will not go to the next step.
        if (flagEntities) {
            //creating all the where criterias
            int curVariableParam = 0;
            for (int x = 0; x < hazardList.size(); x++) {
                StringBuilder tmpString = new StringBuilder();
                curVariableParam = x;

                tmpString.append("AND ");
                if (hazardList.get(x).getEntity3Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity3Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                } else if (hazardList.get(x).getEntity2Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                } else if (hazardList.get(x).getEntity1Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                }
            }

            for (treeNodeObject tmpTreeNode : sbsList) {
                inStringTree.add(tmpTreeNode.getNodeId());
            }

            if (inStringTree.size() > 0) {
                StringBuilder tmpString = new StringBuilder();
                tmpString.append("AND ");
                tmpString.append("HSbs.dbHazardSbsPK.sbsId");
                tmpString.append(logicOperator("in", curVariableParam + 1));
                querySTR = querySTR + tmpString.toString();
            } else {
                flagList = false;
                System.out.println("ejb.DbHazardFacade.findHazardsBySbs(): "
                        + "The list received is empty.");
            }

            try {
                boolean flagParameters = true;
                int curParamNo = 0;

                Query query = em.createQuery(querySTR);
                for (int x = 0; x < hazardList.size(); x++) {
                    int paramNo = x + 1;
                    curParamNo = paramNo;
                    switch (hazardList.get(x).getFieldType()) {
                        case "string":
                            if ("=".equals(hazardList.get(x).getRelationType())
                                    || "like".equals(hazardList.get(x).getRelationType())) {
                                query.setParameter(paramNo, hazardList.get(x).getUserInput());
                            } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                if (parts.length > 0) {
                                    List<String> inString = new ArrayList<>();
                                    inString.addAll(Arrays.asList(parts));
                                    query.setParameter(paramNo, inString);
                                } else {
                                    flagParameters = false;
                                }
                            }
                            break;
                        case "int":
                            if ("=".equals(hazardList.get(x).getRelationType())
                                    || "like".equals(hazardList.get(x).getRelationType())) {
                                query.setParameter(paramNo, Integer.parseInt(hazardList.get(x).getUserInput()));
                            } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                if (parts.length > 0) {
                                    List<Integer> inInt = new ArrayList<>();
                                    for (String tmpString : parts) {
                                        Integer tmpVal = Integer.parseInt(tmpString);
                                        inInt.add(tmpVal);
                                    }
                                    query.setParameter(paramNo, inInt);
                                } else {
                                    flagParameters = false;
                                }
                            }
                            break;
                        default:
                            flagParameters = false;
                            System.out.println("ejb.DbHazardFacade.findHazardsByFields(): "
                                    + "The fieldType is not allowed.");
                            break;
                    }
                }

                if (flagList) {
                    query.setParameter(curParamNo + 1, inStringTree);
                }

                if (flagParameters && flagList) {
                    resultantList = query.getResultList();
                } else {
                    System.out.println("ejb.DbHazardFacade.findHazardsByFieldsAndSbs(): "
                            + "The function could not find a string separed by commas in a filed marked as IN relationype"
                            + "or The tree list received is empty.");
                }

            } catch (Exception e) {
                throw e;
            }
        }
        return resultantList;
    }

    @Override
    public List<DbHazard> findHazardsBySbs(List<treeNodeObject> sbsList) {
        String querySTR = "SELECT DISTINCT Haz "
                + "FROM DbHazard Haz, DbHazardCause HCau, DbHazardConsequence HCoq, DbControlHazard HCtl,"
                + " DbCause Cau, DbConsequence Coq, DbControl Ctl,  DbHazardSbs HSbs "
                + "WHERE Haz.hazardId = HCau.dbHazardCausePK.hazardId "
                + "AND HCau.dbHazardCausePK.causeId = Cau.causeId "
                + "AND Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId "
                + "AND HCoq.dbHazardConsequencePK.consequenceId = Coq.consequenceId "
                + "AND Haz.hazardId = HCtl.dbControlHazardPK.hazardId "
                + "AND HCtl.dbControlHazardPK.controlId = Ctl.controlId "
                + "AND Haz.hazardId = HSbs.dbHazardSbsPK.hazardId ";
        List<DbHazard> resultantList = new ArrayList<>();
        List<String> inString = new ArrayList<>();
        boolean flagList = true;

        for (treeNodeObject tmpTreeNode : sbsList) {
            inString.add(tmpTreeNode.getNodeId());
        }

        if (inString.size() > 0) {
            StringBuilder tmpString = new StringBuilder();
            tmpString.append("AND ");
            tmpString.append("HSbs.dbHazardSbsPK.sbsId");
            tmpString.append(logicOperator("in", 0));
            querySTR = querySTR + tmpString.toString();
        } else {
            flagList = false;
            System.out.println("ejb.DbHazardFacade.findHazardsBySbs(): "
                    + "The list received is empty.");
        }

        if (flagList) {
            try {
                Query query = em.createQuery(querySTR);
                query.setParameter(1, inString);
                resultantList = query.getResultList();
            } catch (Exception e) {
                throw e;
            }
        }

        return resultantList;
    }

    @Override
    public List<DbHazard> findHazardsByFieldsOnly(List<searchObject> hazardList) {
        String querySTR = "SELECT DISTINCT Haz FROM DbHazard Haz ";
        List<DbHazard> resultantList = new ArrayList<>();
        boolean flagEntities = true;
        boolean flagInitialCycle = true;

        //Adding the alias table according to each object in the list
        for (int x = 0; x < hazardList.size(); x++) {
            switch (hazardList.get(x).getEntity1Name()) {
                case "DbHazard":
                    hazardList.get(x).setTableAlias("Haz");
                    break;
                default:
                    flagEntities = false;
                    break;
            }
        }

        //if some unkown entity was introduced in the list, the function will not go to the next step.
        if (flagEntities) {
            //creating all the where criterias
            for (int x = 0; x < hazardList.size(); x++) {
                StringBuilder tmpString = new StringBuilder();

                if (flagInitialCycle) {
                    flagInitialCycle = false;
                    tmpString.append("WHERE ");
                } else {
                    tmpString.append("AND ");
                }

                if (hazardList.get(x).getEntity3Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity3Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                } else if (hazardList.get(x).getEntity2Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                } else if (hazardList.get(x).getEntity1Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    querySTR = querySTR + tmpString.toString();
                }
            }

            try {
                boolean flagParameters = true;
                Query query = em.createQuery(querySTR);
                for (int x = 0; x < hazardList.size(); x++) {
                    int paramNo = x + 1;
                    switch (hazardList.get(x).getFieldType()) {
                        case "string":
                            if ("=".equals(hazardList.get(x).getRelationType())
                                    || "like".equals(hazardList.get(x).getRelationType())) {
                                query.setParameter(paramNo, hazardList.get(x).getUserInput());
                            } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                if (parts.length > 0) {
                                    List<String> inString = new ArrayList<>();
                                    inString.addAll(Arrays.asList(parts));
                                    query.setParameter(paramNo, inString);
                                } else {
                                    flagParameters = false;
                                }
                            }
                            break;
                        case "int":
                            if ("=".equals(hazardList.get(x).getRelationType())
                                    || "like".equals(hazardList.get(x).getRelationType())) {
                                query.setParameter(paramNo, Integer.parseInt(hazardList.get(x).getUserInput()));
                            } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                if (parts.length > 0) {
                                    List<Integer> inInt = new ArrayList<>();
                                    for (String tmpString : parts) {
                                        Integer tmpVal = Integer.parseInt(tmpString);
                                        inInt.add(tmpVal);
                                    }
                                    query.setParameter(paramNo, inInt);
                                } else {
                                    flagParameters = false;
                                }
                            }
                            break;
                        case "date":
                            if ("=".equals(hazardList.get(x).getRelationType())) {
                                query.setParameter(paramNo, Date.valueOf(hazardList.get(x).getUserInput()));
                            }
                            break;
                        default:
                            flagParameters = false;
                            System.out.println("ejb.DbHazardFacade.findHazardsByFieldsOnly(): "
                                    + "The fieldType is not allowed.");
                            break;
                    }
                }

                if (flagParameters) {
                    resultantList = query.getResultList();
                } else {
                    System.out.println("ejb.DbHazardFacade.findHazardsByFieldsOnly(): "
                            + "The function could not find a string separed by commas in a filed marked as IN relationype.");
                }

            } catch (Exception e) {
                throw e;
            }

        } else {
            System.out.println("ejb.DbHazardFacade.findHazardsByFieldsOnly(): "
                    + "Some entites could not be matched with the managed by this function.");
        }

        return resultantList;
    }

    @Override
    public List<DbHazard> validateHazardId(String hazardId) {
        return em.createQuery("FROM DbHazard h WHERE h.hazardId = :checkHazardId")
                .setParameter("checkHazardId", hazardId)
                .getResultList();
    }

    @Override
    public int getRiskScore(int frequencyScore, int severityScore) {
        int riskScore = frequencyScore * severityScore;
        if (riskScore == 0) {
            riskScore = frequencyScore - 1;
            if (riskScore == 0) {
                return 1;
            }
        }
        return riskScore;
    }

    @Override
    public List<DbHazard> getHazardsFromCause(int causeId) {
        return em.createQuery("SELECT h FROM DbHazard h WHERE EXISTS(SELECT 'x' "
                + "FROM DbHazardCause hc WHERE hc.dbHazard.hazardId = h.hazardId "
                + "AND hc.dbCause.causeId = ?1)")
                .setParameter(1, causeId)
                .getResultList();
    }

    @Override
    public List<DbHazard> getHazardsFromConsequence(int consequenceId) {
        return em.createQuery("SELECT h FROM DbHazard h WHERE EXISTS(SELECT 'x' "
                + "FROM DbHazardConsequence hc WHERE hc.dbHazard.hazardId = h.hazardId "
                + "AND hc.dbConsequence.consequenceId = ?1)")
                .setParameter(1, consequenceId)
                .getResultList();
    }

    @Override
    public List<DbHazard> getHazardsFromControl(int controlId) {
        return em.createQuery("SELECT h FROM DbHazard h WHERE EXISTS(SELECT 'x' "
                + "FROM DbControlHazard hc WHERE hc.dbHazard.hazardId = h.hazardId "
                + "AND hc.dbControl.controlId = ?1)")
                .setParameter(1, controlId)
                .getResultList();
    }

    @Override
    //This method consolidates all the previous methods to retrieve hazards data.
    //The rqstType parameter validates if the answer should be the complete or incomplete data.
    public List<DbHazard> findHazards(List<searchObject> hazardList, List<treeNodeObject> sbsList, String rqstType) {
        List<DbHazard> resultantList = new ArrayList<>();
        List<String> inStringTree = new ArrayList<>();
        
        if (rqstType.equals("")) rqstType = "I";
        validateIdObject queryString = createQueryString(hazardList, sbsList, rqstType);

        if (queryString.isValidationFlag()) {
            try {
                int curParamNo = 0;
                boolean flagParameters = true;

                Query query = em.createQuery(queryString.getAnswerString());
                if (!hazardList.isEmpty()) {
                    for (int x = 0; x < hazardList.size(); x++) {
                        curParamNo++;
                        switch (hazardList.get(x).getFieldType()) {
                            case "string":
                                if ("=".equals(hazardList.get(x).getRelationType())
                                        || "like".equals(hazardList.get(x).getRelationType())) {
                                    query.setParameter(curParamNo, hazardList.get(x).getUserInput());
                                } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                    String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                    if (parts.length > 0) {
                                        List<String> inString = new ArrayList<>();
                                        inString.addAll(Arrays.asList(parts));
                                        query.setParameter(curParamNo, inString);
                                    } else {
                                        flagParameters = false;
                                    }
                                }
                                break;
                            case "int":
                                if ("=".equals(hazardList.get(x).getRelationType())
                                        || "like".equals(hazardList.get(x).getRelationType())) {
                                    query.setParameter(curParamNo, Integer.parseInt(hazardList.get(x).getUserInput()));
                                } else if ("in".equals(hazardList.get(x).getRelationType())) {
                                    String parts[] = hazardList.get(x).getUserInput().split("\\,");
                                    if (parts.length > 0) {
                                        List<Integer> inInt = new ArrayList<>();
                                        for (String tmpString : parts) {
                                            Integer tmpVal = Integer.parseInt(tmpString);
                                            inInt.add(tmpVal);
                                        }
                                        query.setParameter(curParamNo, inInt);
                                    } else {
                                        flagParameters = false;
                                    }
                                }
                                break;
                            default:
                                flagParameters = false;
                                System.out.println("ejb.DbHazardFacade.findHazardsByFields(): "
                                        + "The fieldType is not allowed.");
                                break;
                        }
                    }
                }

                if (!sbsList.isEmpty()) {
                    for (treeNodeObject tmpTreeNode : sbsList) {
                        inStringTree.add(tmpTreeNode.getNodeId());
                    }
                    query.setParameter(curParamNo + 1, inStringTree);
                }

                if (flagParameters) {
                   resultantList = query.getResultList(); 
                }
            } catch (Exception e) {
                System.out.println("ejb.DbHazardFacade.findHazards()" + e);
            }
        } else {
            System.out.println(queryString.getAnswerString());
        }
        return resultantList;
    }

    //Performs the logic to construct the dynamic query
    private validateIdObject createQueryString(List<searchObject> hazardList, List<treeNodeObject> sbsList, String rqstType) {
        String qrySelect = "SELECT DISTINCT Haz ";
        String qryFrom = "FROM DbHazard Haz ";
        String qryWhere = "WHERE Haz.hazardId LIKE CONCAT('%',0,'%') "; //THIS LINE SHOULD BE MODIFIED WITH THE HAZARD STATUS
        boolean flagEntities = true;

        //Adding the alias table according to each object in the list
        for (int x = 0; x < hazardList.size(); x++) {
            switch (hazardList.get(x).getEntity1Name()) {
                case "DbHazard":
                    hazardList.get(x).setTableAlias("Haz");
                    break;
                case "DbHazardCause":
                    hazardList.get(x).setTableAlias("HCau");
                    break;
                case "DbHazardConsequence":
                    hazardList.get(x).setTableAlias("HCoq");
                    break;
                case "DbControlHazard":
                    hazardList.get(x).setTableAlias("HCtl");
                    break;
                case "DbCause":
                    hazardList.get(x).setTableAlias("Cau");
                    break;
                case "DbConsequence":
                    hazardList.get(x).setTableAlias("Coq");
                    break;
                case "DbControl":
                    hazardList.get(x).setTableAlias("Ctl");
                    break;
                default:
                    flagEntities = false;
                    break;
            }
        }

        if (flagEntities) {
            if (rqstType.equals("I")) {
                if (!hazardList.isEmpty() || !sbsList.isEmpty()) {
                    twoStringObjs tmpResponse = constructFrom(qryFrom, qryWhere, hazardList, sbsList);
                    qryFrom = tmpResponse.fromField;
                    qryWhere = tmpResponse.whereField;
                    qryWhere = constructWhere(qryWhere, hazardList, sbsList);
                }
                return new validateIdObject(true, qrySelect + qryFrom + qryWhere);
            } else if (rqstType.equals("C")) {
                qryFrom = "FROM DbHazard Haz, DbHazardCause HCau, DbHazardConsequence HCoq, DbControlHazard HCtl, "
                        + "DbCause Cau, DbConsequence Coq, DbControl Ctl, DbHazardSbs HSbs ";
                qryWhere = "WHERE Haz.hazardId = HCau.dbHazardCausePK.hazardId "
                        + "AND HCau.dbHazardCausePK.causeId = Cau.causeId "
                        + "AND Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId "
                        + "AND HCoq.dbHazardConsequencePK.consequenceId = Coq.consequenceId "
                        + "AND Haz.hazardId = HCtl.dbControlHazardPK.hazardId "
                        + "AND HCtl.dbControlHazardPK.controlId = Ctl.controlId "
                        + "AND Haz.hazardId = HSbs.dbHazardSbsPK.hazardId ";
                if (!hazardList.isEmpty() || !sbsList.isEmpty()) {
                    qryWhere = constructWhere(qryWhere, hazardList, sbsList);
                }
                return new validateIdObject(true, qrySelect + qryFrom + qryWhere);
            }
            return new validateIdObject(false, "ejb.DbHazardFacade.createQueryString(): "
                    + "The requested hazard type does not match with complete or incomplete.");
        }
        return new validateIdObject(false, "ejb.DbHazardFacade.createQueryString(): "
                + "Some entites could not be matched with the managed by this function.");
    }

    //Performs the logic to build the dynamic FROM AND WHERE JOIN TABLES SQL statements.
    private twoStringObjs constructFrom(String qryFrom, String qryWhere, List<searchObject> hazardList, List<treeNodeObject> sbsList) {
        twoStringObjs responseObj = new twoStringObjs();
        StringBuilder fromString = new StringBuilder(qryFrom);
        StringBuilder whereString = new StringBuilder(qryWhere);
        boolean causeAdded = false;
        boolean conqsAdded = false;
        boolean controlAdded = false;

        if (!hazardList.isEmpty()) {
            for (int x = 0; x < hazardList.size(); x++) {
                switch (hazardList.get(x).getEntity1Name()) {
                    case "DbCause":
                    case "DbHazardCause":
                        if (!causeAdded) {
                            causeAdded = true;
                            fromString.append(",DbHazardCause HCau ,DbCause Cau ");
                            whereString.append("AND Haz.hazardId = HCau.dbHazardCausePK.hazardId AND HCau.dbHazardCausePK.causeId = Cau.causeId ");
                        }
                        break;
                    case "DbConsequence":
                    case "DbHazardConsequence":
                        if (!conqsAdded) {
                            conqsAdded = true;
                            fromString.append(",DbHazardConsequence HCoq ,DbConsequence Coq ");
                            whereString.append("AND Haz.hazardId = HCoq.dbHazardConsequencePK.hazardId AND HCoq.dbHazardConsequencePK.consequenceId = Coq.consequenceId ");
                        }
                        break;
                    case "DbControl":
                    case "DbControlHazard":
                        if (!controlAdded) {
                            controlAdded = true;
                            fromString.append(",DbControlHazard HCtl ,DbControl Ctl ");
                            whereString.append("AND Haz.hazardId = HCtl.dbControlHazardPK.hazardId AND HCtl.dbControlHazardPK.controlId = Ctl.controlId ");
                        }
                        break;
                }
            }
        }

        if (!sbsList.isEmpty()) {
            fromString.append(",DbHazardSbs HSbs ");
            whereString.append("AND Haz.hazardId = HSbs.dbHazardSbsPK.hazardId ");
        }

        responseObj.fromField = fromString.toString();
        responseObj.whereField = whereString.toString();

        return responseObj;
    }

    //Performs the lodic to create the WHERE SQL criteria
    private String constructWhere(String qryWhere, List<searchObject> hazardList, List<treeNodeObject> sbsList) {
        int curVariableParam = 0;
        StringBuilder whereFields = new StringBuilder(qryWhere);

        if (!hazardList.isEmpty()) {
            for (int x = 0; x < hazardList.size(); x++) {
                StringBuilder tmpString = new StringBuilder();
                curVariableParam++;
                tmpString.append("AND ");
                if (hazardList.get(x).getEntity3Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity3Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    whereFields.append(tmpString.toString());
                } else if (hazardList.get(x).getEntity2Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getEntity2Name());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    whereFields.append(tmpString.toString());
                } else if (hazardList.get(x).getEntity1Name() != null) {
                    tmpString.append(hazardList.get(x).getTableAlias());
                    tmpString.append(".");
                    tmpString.append(hazardList.get(x).getFieldName());
                    tmpString.append(logicOperator(hazardList.get(x).getRelationType(), x));
                    whereFields.append(tmpString.toString());
                }
                
            }
        }

        if (!sbsList.isEmpty()) {
            StringBuilder tmpString = new StringBuilder();
            tmpString.append("AND ");
            tmpString.append("HSbs.dbHazardSbsPK.sbsId");
            tmpString.append(logicOperator("in", curVariableParam));
            whereFields.append(tmpString.toString());
        }

        return whereFields.toString();
    }

    //Create the where line according to the searched type
    private String logicOperator(String relationType, int paramNo) {
        String finalSTR = "";
        paramNo++;
        switch (relationType) {
            case "=":
                finalSTR = " = ?" + paramNo + " ";
                break;
            case "like":
                finalSTR = " LIKE CONCAT('%', ?" + paramNo + ", '%') ";
                break;
            case "in":
                finalSTR = " IN ?" + paramNo + " ";
                break;
        }
        return finalSTR;
    }

    class twoStringObjs {

        public String fromField;
        public String whereField;
    }
}
