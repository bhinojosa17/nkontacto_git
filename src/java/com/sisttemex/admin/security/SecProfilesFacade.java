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
public class SecProfilesFacade extends AbstractFacade<SecProfiles> {
    @PersistenceContext(unitName = "NKontactoPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public SecProfilesFacade() {
        super(SecProfiles.class);
    }
    
}
