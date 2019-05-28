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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "sec_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecUsers.findAll", query = "SELECT s FROM SecUsers s"),
    @NamedQuery(name = "SecUsers.findByUserId", query = "SELECT s FROM SecUsers s WHERE s.userId = :userId"),
    @NamedQuery(name = "SecUsers.findByUsername", query = "SELECT s FROM SecUsers s WHERE s.username = :username"),
    @NamedQuery(name = "SecUsers.findByName", query = "SELECT s FROM SecUsers s WHERE s.name = :name"),
    @NamedQuery(name = "SecUsers.findByLastname", query = "SELECT s FROM SecUsers s WHERE s.lastname = :lastname"),
    @NamedQuery(name = "SecUsers.findByPassword", query = "SELECT s FROM SecUsers s WHERE s.password = :password"),
    @NamedQuery(name = "SecUsers.findByEmail", query = "SELECT s FROM SecUsers s WHERE s.email = :email"),
    @NamedQuery(name = "SecUsers.findByStatusReg", query = "SELECT s FROM SecUsers s WHERE s.statusReg = :statusReg"),
    @NamedQuery(name = "SecUsers.findByCreatedOn", query = "SELECT s FROM SecUsers s WHERE s.createdOn = :createdOn"),
    @NamedQuery(name = "SecUsers.findByUpdatedOn", query = "SELECT s FROM SecUsers s WHERE s.updatedOn = :updatedOn"),
    @NamedQuery(name = "SecUsers.findByLastSessionDate", query = "SELECT s FROM SecUsers s WHERE s.lastSessionDate = :lastSessionDate"),
    @NamedQuery(name = "SecUsers.findByTestEnv", query = "SELECT s FROM SecUsers s WHERE s.testEnv = :testEnv"),
    @NamedQuery(name = "SecUsers.findByUserPwd", query = "SELECT s FROM SecUsers s WHERE s.username = :username and s.password= :password")})
public class SecUsers implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy")
    private Collection<SecRoles> secRolesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy")
    private Collection<SecProfiles> secProfilesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy")
    private Collection<SecProfilesRoles> secProfilesRolesCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "password")
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status_reg")
    private boolean statusReg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_session_date")
    @Temporal(TemporalType.DATE)
    private Date lastSessionDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "test_env")
    private boolean testEnv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy")
    private Collection<SecDomains> secDomainsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy")
    private Collection<SecUsers> secUsersCollection;
    @JoinColumn(name = "updated_by", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private SecUsers updatedBy;
    @JoinColumn(name = "domain_id", referencedColumnName = "domain_id")
    @ManyToOne(optional = false)
    private SecDomains domainId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    private Collection<SecUsers> secUsersCollection1;
    @JoinColumn(name = "created_by", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private SecUsers createdBy;
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @ManyToOne(optional = false)
    private SecCompanies companyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy")
    private Collection<SecCompanies> secCompaniesCollection;

    public SecUsers() {
    }

    public SecUsers(Integer userId) {
        this.userId = userId;
    }

    public SecUsers(Integer userId, String username, String name, String lastname, String password, String email, boolean statusReg, Date createdOn, Date updatedOn, Date lastSessionDate, boolean testEnv) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.statusReg = statusReg;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.lastSessionDate = lastSessionDate;
        this.testEnv = testEnv;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getStatusReg() {
        return statusReg;
    }

    public void setStatusReg(boolean statusReg) {
        this.statusReg = statusReg;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Date getLastSessionDate() {
        return lastSessionDate;
    }

    public void setLastSessionDate(Date lastSessionDate) {
        this.lastSessionDate = lastSessionDate;
    }

    public boolean getTestEnv() {
        return testEnv;
    }

    public void setTestEnv(boolean testEnv) {
        this.testEnv = testEnv;
    }

    @XmlTransient
    public Collection<SecDomains> getSecDomainsCollection() {
        return secDomainsCollection;
    }

    public void setSecDomainsCollection(Collection<SecDomains> secDomainsCollection) {
        this.secDomainsCollection = secDomainsCollection;
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

    public SecDomains getDomainId() {
        return domainId;
    }

    public void setDomainId(SecDomains domainId) {
        this.domainId = domainId;
    }

    @XmlTransient
    public Collection<SecUsers> getSecUsersCollection1() {
        return secUsersCollection1;
    }

    public void setSecUsersCollection1(Collection<SecUsers> secUsersCollection1) {
        this.secUsersCollection1 = secUsersCollection1;
    }

    public SecUsers getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SecUsers createdBy) {
        this.createdBy = createdBy;
    }

    public SecCompanies getCompanyId() {
        return companyId;
    }

    public void setCompanyId(SecCompanies companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<SecCompanies> getSecCompaniesCollection() {
        return secCompaniesCollection;
    }

    public void setSecCompaniesCollection(Collection<SecCompanies> secCompaniesCollection) {
        this.secCompaniesCollection = secCompaniesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecUsers)) {
            return false;
        }
        SecUsers other = (SecUsers) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username;
    }

    @XmlTransient
    public Collection<SecRoles> getSecRolesCollection() {
        return secRolesCollection;
    }

    public void setSecRolesCollection(Collection<SecRoles> secRolesCollection) {
        this.secRolesCollection = secRolesCollection;
    }

    @XmlTransient
    public Collection<SecProfiles> getSecProfilesCollection() {
        return secProfilesCollection;
    }

    public void setSecProfilesCollection(Collection<SecProfiles> secProfilesCollection) {
        this.secProfilesCollection = secProfilesCollection;
    }

    @XmlTransient
    public Collection<SecProfilesRoles> getSecProfilesRolesCollection() {
        return secProfilesRolesCollection;
    }

    public void setSecProfilesRolesCollection(Collection<SecProfilesRoles> secProfilesRolesCollection) {
        this.secProfilesRolesCollection = secProfilesRolesCollection;
    }

}
