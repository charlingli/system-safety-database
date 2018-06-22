/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbsystemParameters;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbsystemParametersFacade extends AbstractFacade<DbsystemParameters> implements DbsystemParametersFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbsystemParametersFacade() {
        super(DbsystemParameters.class);
    }
    
}
