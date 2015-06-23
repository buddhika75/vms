/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.faces;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vms.entity.Milage;

/**
 *
 * @author Buddhika
 */
@Stateless
public class MilageFacade extends AbstractFacade<Milage> {
    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MilageFacade() {
        super(Milage.class);
    }
    
}
