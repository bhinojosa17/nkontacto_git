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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bruce Hinojosa
 */
@Entity
@Table(name = "sec_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecMenu.findAll", query = "SELECT s FROM SecMenu s"),
    @NamedQuery(name = "SecMenu.findByItemId", query = "SELECT s FROM SecMenu s WHERE s.itemId = :itemId"),
    @NamedQuery(name = "SecMenu.findByNotItemId", query = "SELECT s FROM SecMenu s WHERE s.itemId <> :itemId"),
    @NamedQuery(name = "SecMenu.findByTextMenu", query = "SELECT s FROM SecMenu s WHERE s.textMenu = :textMenu"),
    @NamedQuery(name = "SecMenu.findByIdParent", query = "SELECT s FROM SecMenu s WHERE s.idParent = :idParent"),
    @NamedQuery(name = "SecMenu.findByLink", query = "SELECT s FROM SecMenu s WHERE s.link = :link"),
    @NamedQuery(name = "SecMenu.findByStatusReg", query = "SELECT s FROM SecMenu s WHERE s.statusReg = :statusReg"),
    @NamedQuery(name = "SecMenu.findByUpdatedOn", query = "SELECT s FROM SecMenu s WHERE s.updatedOn = :updatedOn"),
    @NamedQuery(name = "SecMenu.findByUpdatedBy", query = "SELECT s FROM SecMenu s WHERE s.updatedBy = :updatedBy")})
public class SecMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "item_id")
    private Integer itemId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "text_menu")
    private String textMenu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_parent")
    private int idParent;
    @Size(max = 200)
    @Column(name = "link")
    private String link;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status_reg")
    private short statusReg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_by")
    private int updatedBy;

    public SecMenu() {
    }

    public SecMenu(Integer itemId) {
        this.itemId = itemId;
    }

    public SecMenu(Integer itemId, String textMenu, int idParent, short statusReg, Date updatedOn, int updatedBy) {
        this.itemId = itemId;
        this.textMenu = textMenu;
        this.idParent = idParent;
        this.statusReg = statusReg;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTextMenu() {
        return textMenu;
    }

    public void setTextMenu(String textMenu) {
        this.textMenu = textMenu;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public short getStatusReg() {
        return statusReg;
    }

    public void setStatusReg(short statusReg) {
        this.statusReg = statusReg;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecMenu)) {
            return false;
        }
        SecMenu other = (SecMenu) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sisttemex.admin.security.SecMenu[ itemId=" + itemId + " ]";
    }
    
}
