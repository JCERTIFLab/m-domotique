/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.domotique.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author FirasGabsi
 */
@Entity
@Table(name = "TYPE_EQUIPEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeEquipement.findAll", query = "SELECT t FROM TypeEquipement t"),
    @NamedQuery(name = "TypeEquipement.findById", query = "SELECT t FROM TypeEquipement t WHERE t.id = :id"),
    @NamedQuery(name = "TypeEquipement.findByNom", query = "SELECT t FROM TypeEquipement t WHERE t.nom = :nom")})
public class TypeEquipement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOM")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "IMG")
    private String img;
    @OneToMany(mappedBy = "typeId",cascade = CascadeType.REMOVE)
    private Collection<Equipement> equipementCollection;

    public TypeEquipement() {
    }

    public TypeEquipement(Integer id) {
        this.id = id;
    }

    public TypeEquipement(Integer id, String nom,String img) {
        this.id = id;
        this.nom = nom;
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    

    @XmlTransient
    public Collection<Equipement> getEquipementCollection() {
        return equipementCollection;
    }

    public void setEquipementCollection(Collection<Equipement> equipementCollection) {
        this.equipementCollection = equipementCollection;
    }
    
    public String getImf() {
        return img;
    }

    public void setImf(String img) {
        this.img = img;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeEquipement)) {
            return false;
        }
        TypeEquipement other = (TypeEquipement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jcertif.domotique.entity.TypeEquipement[ id=" + id + " ]";
    }
    
}
