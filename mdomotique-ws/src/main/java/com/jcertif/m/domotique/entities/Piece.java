/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.m.domotique.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import org.codehaus.jackson.annotate.JsonIgnore;


@Entity
@Table(name = "PIECE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Piece.findAll", query = "SELECT p FROM Piece p"),
    @NamedQuery(name = "Piece.findById", query = "SELECT p FROM Piece p WHERE p.id = :id"),
    @NamedQuery(name = "Piece.findByNom", query = "SELECT p FROM Piece p WHERE p.nom = :nom")})
public class Piece implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOM", nullable = false, length = 30)
    private String nom;
    @JoinColumn(name = "ETABLISSEMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Etablissement etablissementId;
    @OneToMany(mappedBy = "pieceId")
    private List<Equipement> equipementList;

    public Piece() {
    }

    public Piece(Integer id) {
        this.id = id;
    }

    public Piece(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
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

    public Etablissement getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(Etablissement etablissementId) {
        this.etablissementId = etablissementId;
    }

    @XmlTransient
    @JsonIgnore
    public List<Equipement> getEquipementList() {
        return equipementList;
    }

    public void setEquipementList(List<Equipement> equipementList) {
        this.equipementList = equipementList;
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
        if (!(object instanceof Piece)) {
            return false;
        }
        Piece other = (Piece) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jcertif.m.domotique.entities.Piece[ id=" + id + " ]";
    }
    
}
