/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sisttemex.admin.security;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Bruce Hinojosa
 */
@Stateless
public class SecCompaniesFacade extends AbstractFacade<SecCompanies> {
    @PersistenceContext(unitName = "NKontactoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecCompaniesFacade() {
        super(SecCompanies.class);
    }
    
}
