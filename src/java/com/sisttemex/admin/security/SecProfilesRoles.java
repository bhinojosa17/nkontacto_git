/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sisttemex.admin.security;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bruce Hinojosa
 */
@Entity
@Table(name = "sec_profiles_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecProfilesRoles.findAll", query = "SELECT s FROM SecProfilesRoles s"),
    @NamedQuery(name = "SecProfilesRoles.findByProfileId", query = "SELECT s FROM SecProfilesRoles s WHERE s.secProfilesRolesPK.profileId = :profileId"),
    @NamedQuery(name = "SecProfilesRoles.findByRoleId", query = "SELECT s FROM SecProfilesRoles s WHERE s.secProfilesRolesPK.roleId = :roleId"),
    @NamedQuery(name = "SecProfilesRoles.findByStatusReg", query = "SELECT s FROM SecProfilesRoles s WHERE s.statusReg = :statusReg"),
    @NamedQuery(name = "SecProfilesRoles.findByUpdatedOn", query = "SELECT s FROM SecProfilesRoles s WHERE s.updatedOn = :updatedOn"),
    @NamedQuery(name = "SecProfilesRoles.deleteFromProfile", query = "DELETE FROM SecProfilesRoles spr WHERE spr.secProfilesRolesPK.profileId= :profileId")
})

public class SecProfilesRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SecProfilesRolesPK secProfilesRolesPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status_reg")
    private boolean statusReg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    @JoinColumn(name = "updated_by", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private SecUsers updatedBy;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SecRoles secRoles;
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SecProfiles secProfiles;

    public SecProfilesRoles() {
        
        statusReg=true;
    }

    public SecProfilesRoles(SecProfilesRolesPK secProfilesRolesPK) {
        this.secProfilesRolesPK = secProfilesRolesPK;
    }

    public SecProfilesRoles(SecProfilesRolesPK secProfilesRolesPK, boolean statusReg, Date updatedOn) {
        this.secProfilesRolesPK = secProfilesRolesPK;
        this.statusReg = statusReg;
        this.updatedOn = updatedOn;
    }

    public SecProfilesRoles(String profileId, String roleId) {
        this.secProfilesRolesPK = new SecProfilesRolesPK(profileId, roleId);
    }

    public SecProfilesRolesPK getSecProfilesRolesPK() {
        return secProfilesRolesPK;
    }

    public void setSecProfilesRolesPK(SecProfilesRolesPK secProfilesRolesPK) {
        this.secProfilesRolesPK = secProfilesRolesPK;
    }

    public boolean getStatusReg() {
        return statusReg;
    }

    public void setStatusReg(boolean statusReg) {
        this.statusReg = statusReg;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SecUsers getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(SecUsers updatedBy) {
        this.updatedBy = updatedBy;
    }

    public SecRoles getSecRoles() {
        
      
        return secRoles;
    }

    public void setSecRoles(SecRoles secRoles) {
        this.secRoles = secRoles;
    }

    public SecProfiles getSecProfiles() {
        return secProfiles;
    }

    public void setSecProfiles(SecProfiles secProfiles) {
        this.secProfiles = secProfiles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secProfilesRolesPK != null ? secProfilesRolesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecProfilesRoles)) {
            return false;
        }
        SecProfilesRoles other = (SecProfilesRoles) object;
        if ((this.secProfilesRolesPK == null && other.secProfilesRolesPK != null) || (this.secProfilesRolesPK != null && !this.secProfilesRolesPK.equals(other.secProfilesRolesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "["+ secProfilesRolesPK + " ]";
    }
    
}
