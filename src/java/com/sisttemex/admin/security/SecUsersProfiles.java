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
@Table(name = "sec_users_profiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecUsersProfiles.findAll", query = "SELECT s FROM SecUsersProfiles s"),
    @NamedQuery(name = "SecUsersProfiles.findByUserId", query = "SELECT s FROM SecUsersProfiles s WHERE s.secUsersProfilesPK.userId = :userId"),
    @NamedQuery(name = "SecUsersProfiles.findByProfileId", query = "SELECT s FROM SecUsersProfiles s WHERE s.secUsersProfilesPK.profileId = :profileId"),
    @NamedQuery(name = "SecUsersProfiles.findByUpdatedOn", query = "SELECT s FROM SecUsersProfiles s WHERE s.updatedOn = :updatedOn"),
    @NamedQuery(name = "SecUsersProfiles.deleteFromUser", query = "DELETE FROM SecUsersProfiles sup WHERE sup.secUsersProfilesPK.userId= :userId")})
public class SecUsersProfiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SecUsersProfilesPK secUsersProfilesPK;
    @JoinColumn(name = "updated_by", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private SecUsers updatedBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    public SecUsersProfiles() {
    }

    public SecUsersProfiles(SecUsersProfilesPK secUsersProfilesPK) {
        this.secUsersProfilesPK = secUsersProfilesPK;
    }

    public SecUsersProfiles(SecUsersProfilesPK secUsersProfilesPK, Date updatedOn) {
        this.secUsersProfilesPK = secUsersProfilesPK;
        this.updatedOn = updatedOn;
    }

    public SecUsersProfiles(int userId, String profileId) {
        this.secUsersProfilesPK = new SecUsersProfilesPK(userId, profileId);
    }

    public SecUsersProfilesPK getSecUsersProfilesPK() {
        return secUsersProfilesPK;
    }

    public void setSecUsersProfilesPK(SecUsersProfilesPK secUsersProfilesPK) {
        this.secUsersProfilesPK = secUsersProfilesPK;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secUsersProfilesPK != null ? secUsersProfilesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecUsersProfiles)) {
            return false;
        }
        SecUsersProfiles other = (SecUsersProfiles) object;
        if ((this.secUsersProfilesPK == null && other.secUsersProfilesPK != null) || (this.secUsersProfilesPK != null && !this.secUsersProfilesPK.equals(other.secUsersProfilesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sisttemex.admin.security.SecUsersProfiles[ secUsersProfilesPK=" + secUsersProfilesPK + " ]";
    }

    /**
     * @return the updatedBy
     */
    public SecUsers getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(SecUsers updatedBy) {
        this.updatedBy = updatedBy;
    }

}
