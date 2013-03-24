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

/**
 *
 * @author bashizip
 */
@Entity
@Table(name = "ETABLISSEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etablissement.findAll", query = "SELECT e FROM Etablissement e"),
    @NamedQuery(name = "Etablissement.findById", query = "SELECT e FROM Etablissement e WHERE e.id = :id"),
    @NamedQuery(name = "Etablissement.findByNom", query = "SELECT e FROM Etablissement e WHERE e.nom = :nom"),
    @NamedQuery(name = "Etablissement.findByTel", query = "SELECT e FROM Etablissement e WHERE e.tel = :tel"),
    @NamedQuery(name = "Etablissement.findByAdresse", query = "SELECT e FROM Etablissement e WHERE e.adresse = :adresse")})
public class Etablissement implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TEL", nullable = false, length = 30)
    private String tel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ADRESSE", nullable = false, length = 30)
    private String adresse;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private User userId;
    @OneToMany(mappedBy = "etablissementId")
    private List<Piece> pieceList;

    public Etablissement() {
    }

    public Etablissement(Integer id) {
        this.id = id;
    }

    public Etablissement(Integer id, String nom, String tel, String adresse) {
        this.id = id;
        this.nom = nom;
        this.tel = tel;
        this.adresse = adresse;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @XmlTransient
    @JsonIgnore
    public List<Piece> getPieceList() {
        return pieceList;
    }

    public void setPieceList(List<Piece> pieceList) {
        this.pieceList = pieceList;
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
        if (!(object instanceof Etablissement)) {
            return false;
        }
        Etablissement other = (Etablissement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jcertif.m.domotique.entities.Etablissement[ id=" + id + " ]";
    }
    
}
