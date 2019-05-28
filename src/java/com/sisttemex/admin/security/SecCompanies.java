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
@Table(name = "sec_companies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecCompanies.findAll", query = "SELECT s FROM SecCompanies s"),
    @NamedQuery(name = "SecCompanies.findByCompanyId", query = "SELECT s FROM SecCompanies s WHERE s.companyId = :companyId"),
    @NamedQuery(name = "SecCompanies.findByCompany", query = "SELECT s FROM SecCompanies s WHERE s.company = :company"),
    @NamedQuery(name = "SecCompanies.findByAddress", query = "SELECT s FROM SecCompanies s WHERE s.address = :address"),
    @NamedQuery(name = "SecCompanies.findByZipCode", query = "SELECT s FROM SecCompanies s WHERE s.zipCode = :zipCode"),
    @NamedQuery(name = "SecCompanies.findByContact", query = "SELECT s FROM SecCompanies s WHERE s.contact = :contact"),
    @NamedQuery(name = "SecCompanies.findByPhoneNumber", query = "SELECT s FROM SecCompanies s WHERE s.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "SecCompanies.findByContactMail", query = "SELECT s FROM SecCompanies s WHERE s.contactMail = :contactMail"),
    @NamedQuery(name = "SecCompanies.findByUpdatedOn", query = "SELECT s FROM SecCompanies s WHERE s.updatedOn = :updatedOn"),
    @NamedQuery(name = "SecCompanies.findByStatusReg", query = "SELECT s FROM SecCompanies s WHERE s.statusReg = :statusReg")})
public class SecCompanies implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "company_id")
    private String companyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "company")
    private String company;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "zip_code")
    private String zipCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "contact_mail")
    private String contactMail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status_reg")
    private boolean statusReg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<SecUsers> secUsersCollection;
    @JoinColumn(name = "updated_by", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private SecUsers updatedBy;
    @JoinColumn(name = "city", referencedColumnName = "city_id")
    @ManyToOne(optional = false)
    private GeoCities city;

    public SecCompanies() {
    }

    public SecCompanies(String companyId) {
        this.companyId = companyId;
    }

    public SecCompanies(String companyId, String company, String address, String zipCode, String contact, String phoneNumber, String contactMail, Date updatedOn, boolean statusReg) {
        this.companyId = companyId;
        this.company = company;
        this.address = address;
        this.zipCode = zipCode;
        this.contact = contact;
        this.phoneNumber = phoneNumber;
        this.contactMail = contactMail;
        this.updatedOn = updatedOn;
        this.statusReg = statusReg;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public boolean getStatusReg() {
        return statusReg;
    }

    public void setStatusReg(boolean statusReg) {
        this.statusReg = statusReg;
    }

    @XmlTransient
    public Collection<SecUsers> getSecUsersCollection() {
        return secUsersCollection;
    }

    public void setSecUsersCollection(Collection<SecUsers> secUsersCollection) {
        this.secUsersCollection = secUsersCollection;
    }

    public SecUsers getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(SecUsers updatedBy) {
        this.updatedBy = updatedBy;
    }

    public GeoCities getCity() {
        return city;
    }

    public void setCity(GeoCities city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyId != null ? companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecCompanies)) {
            return false;
        }
        SecCompanies other = (SecCompanies) object;
        if ((this.companyId == null && other.companyId != null) || (this.companyId != null && !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  company ;
    }
    
}
