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
@Table(name = "sec_profiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecProfiles.findAll", query = "SELECT s FROM SecProfiles s"),
    @NamedQuery(name = "SecProfiles.findByProfileId", query = "SELECT s FROM SecProfiles s WHERE s.profileId = :profileId"),
    @NamedQuery(name = "SecProfiles.findByDescr", query = "SELECT s FROM SecProfiles s WHERE s.descr = :descr"),
    @NamedQuery(name = "SecProfiles.findByStatusReg", query = "SELECT s FROM SecProfiles s WHERE s.statusReg = :statusReg"),
    @NamedQuery(name = "SecProfiles.findByUpdatedOn", query = "SELECT s FROM SecProfiles s WHERE s.updatedOn = :updatedOn")})

public class SecProfiles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "profile_id")
    private String profileId;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secProfiles")
    private Collection<SecProfilesRoles> secProfilesRolesCollection;

    public SecProfiles() {
        statusReg=true;
    }

    public SecProfiles(String profileId) {
        this.profileId = profileId;
    }

    public SecProfiles(String profileId, String descr, boolean statusReg, Date updatedOn) {
        this.profileId = profileId;
        this.descr = descr;
        this.statusReg = statusReg;
        this.updatedOn = updatedOn;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
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
        hash += (profileId != null ? profileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecProfiles)) {
            return false;
        }
        SecProfiles other = (SecProfiles) object;
        if ((this.profileId == null && other.profileId != null) || (this.profileId != null && !this.profileId.equals(other.profileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  descr  ;
    }
    
}
