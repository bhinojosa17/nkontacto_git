/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sisttemex.admin.security;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bruce Hinojosa
 */
@Entity
@Table(name = "geo_states")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeoStates.findAll", query = "SELECT g FROM GeoStates g"),
    @NamedQuery(name = "GeoStates.findByStateId", query = "SELECT g FROM GeoStates g WHERE g.stateId = :stateId"),
    @NamedQuery(name = "GeoStates.findByState", query = "SELECT g FROM GeoStates g WHERE g.state = :state")})
public class GeoStates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "state_id")
    private String stateId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "state")
    private String state;
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne(optional = false)
    private GeoCountries countryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateId")
    private Collection<GeoCities> geoCitiesCollection;

    public GeoStates() {
    }

    public GeoStates(String stateId) {
        this.stateId = stateId;
    }

    public GeoStates(String stateId, String state) {
        this.stateId = stateId;
        this.state = state;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public GeoCountries getCountryId() {
        return countryId;
    }

    public void setCountryId(GeoCountries countryId) {
        this.countryId = countryId;
    }

    @XmlTransient
    public Collection<GeoCities> getGeoCitiesCollection() {
        return geoCitiesCollection;
    }

    public void setGeoCitiesCollection(Collection<GeoCities> geoCitiesCollection) {
        this.geoCitiesCollection = geoCitiesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stateId != null ? stateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeoStates)) {
            return false;
        }
        GeoStates other = (GeoStates) object;
        if ((this.stateId == null && other.stateId != null) || (this.stateId != null && !this.stateId.equals(other.stateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sisttemex.admin.security.GeoStates[ stateId=" + stateId + " ]";
    }
    
}
