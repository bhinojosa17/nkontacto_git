/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sisttemex.admin.security;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bruce Hinojosa
 */
@Entity
@Table(name = "geo_cities")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeoCities.findAll", query = "SELECT g FROM GeoCities g"),
    @NamedQuery(name = "GeoCities.findByCityId", query = "SELECT g FROM GeoCities g WHERE g.cityId = :cityId"),
    @NamedQuery(name = "GeoCities.findByCity", query = "SELECT g FROM GeoCities g WHERE g.city = :city")})
public class GeoCities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "city_id")
    private String cityId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "city")
    private String city;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne(optional = false)
    private GeoStates stateId;

    public GeoCities() {
    }

    public GeoCities(String cityId) {
        this.cityId = cityId;
    }

    public GeoCities(String cityId, String city) {
        this.cityId = cityId;
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public GeoStates getStateId() {
        return stateId;
    }

    public void setStateId(GeoStates stateId) {
        this.stateId = stateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cityId != null ? cityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeoCities)) {
            return false;
        }
        GeoCities other = (GeoCities) object;
        if ((this.cityId == null && other.cityId != null) || (this.cityId != null && !this.cityId.equals(other.cityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sisttemex.admin.security.GeoCities[ cityId=" + cityId + " ]";
    }
    
}
