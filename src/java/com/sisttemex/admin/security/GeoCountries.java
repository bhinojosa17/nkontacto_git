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
@Table(name = "geo_countries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeoCountries.findAll", query = "SELECT g FROM GeoCountries g"),
    @NamedQuery(name = "GeoCountries.findByCountryId", query = "SELECT g FROM GeoCountries g WHERE g.countryId = :countryId"),
    @NamedQuery(name = "GeoCountries.findByCountry", query = "SELECT g FROM GeoCountries g WHERE g.country = :country")})
public class GeoCountries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "country_id")
    private String countryId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "country")
    private String country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryId")
    private Collection<GeoStates> geoStatesCollection;

    public GeoCountries() {
    }

    public GeoCountries(String countryId) {
        this.countryId = countryId;
    }

    public GeoCountries(String countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @XmlTransient
    public Collection<GeoStates> getGeoStatesCollection() {
        return geoStatesCollection;
    }

    public void setGeoStatesCollection(Collection<GeoStates> geoStatesCollection) {
        this.geoStatesCollection = geoStatesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryId != null ? countryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeoCountries)) {
            return false;
        }
        GeoCountries other = (GeoCountries) object;
        if ((this.countryId == null && other.countryId != null) || (this.countryId != null && !this.countryId.equals(other.countryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sisttemex.admin.security.GeoCountries[ countryId=" + countryId + " ]";
    }
    
}
