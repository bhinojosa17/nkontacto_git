/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sisttemex.admin.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bruce Hinojosa
 */
@Entity
@Table(name = "sec_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecRoles.findAll", query = "SELECT s FROM SecRoles s"),
    @NamedQuery(name = "SecRoles.findByRoleId", query = "SELECT s FROM SecRoles s WHERE s.roleId = :roleId"),
     @NamedQuery(name = "SecRoles.findByProfileId", query = "SELECT s FROM SecRoles s, SecProfilesRoles spr WHERE s.roleId=spr.secRoles.roleId AND spr.secProfiles.profileId = :profileId"),
    @NamedQuery(name = "SecRoles.findByDescr", query = "SELECT s FROM SecRoles s WHERE s.descr = :descr"),
    @NamedQuery(name = "SecRoles.findByStatusReg", query = "SELECT s FROM SecRoles s WHERE s.statusReg = :statusReg"),
    @NamedQuery(name = "SecRoles.findByUpdatedOn", query = "SELECT s FROM SecRoles s WHERE s.updatedOn = :updatedOn"),
    @NamedQuery(name = "SecRoles.findSuggestRoles", query = "SELECT s FROM SecRoles s WHERE s.roleId NOT IN (SELECT spr.secRoles.roleId FROM SecProfilesRoles spr WHERE spr.secProfiles.profileId = :profileId)")})


public class SecRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "role_id")
    private String roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descr")
    private String descr;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secRoles")
    private Collection<SecProfilesRoles> secProfilesRolesCollection;

    public SecRoles() {
        statusReg=true;
    }

    public SecRoles(String roleId) {
        this.roleId = roleId;
    }

    public SecRoles(String roleId, String descr, boolean statusReg, Date updatedOn) {
        this.roleId = roleId;
        this.descr = descr;
        this.statusReg = statusReg;
        this.updatedOn = updatedOn;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
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

    @XmlTransient
    public Collection<SecProfilesRoles> getSecProfilesRolesCollection() {
        return secProfilesRolesCollection;
    }

    public void setSecProfilesRolesCollection(Collection<SecProfilesRoles> secProfilesRolesCollection) {
        this.secProfilesRolesCollection = secProfilesRolesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecRoles)) {
            return false;
        }
        SecRoles other = (SecRoles) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  roleId  ;
    }
    
}
