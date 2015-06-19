/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.hotel.faces;

import vms.hotel.entity.DepartmentOrInstitution;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author buddhika
 */
@Stateless
public class DepartmentOrInstitutionFacade extends AbstractFacade<DepartmentOrInstitution> {
    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartmentOrInstitutionFacade() {
        super(DepartmentOrInstitution.class);
    }
    
}
