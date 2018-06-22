/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbLocation;
import entities.DbProject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbProjectFacade extends AbstractFacade<DbProject> implements DbProjectFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbProjectFacade() {
        super(DbProject.class);
    }

    @Override
    public List<DbLocation> checkProject(int projectId) {
        return em.createQuery("FROM DbLocation l WHERE l.projectId.projectId = :checkProjectId")
                .setParameter("checkProjectId", projectId)
                .setMaxResults(1)
                .getResultList();
    }

    @Override
    public List<DbProject> checkProjectAbbrev(String projectAbbrev) {
        return em.createQuery("FROM DbProject p WHERE p.projectAbbrev = :checkProjectAbbrev")
                .setParameter("checkProjectAbbrev", projectAbbrev)
                .getResultList();
    }

}
