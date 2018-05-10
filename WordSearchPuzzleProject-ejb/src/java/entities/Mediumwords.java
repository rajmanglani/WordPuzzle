/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rkmanglani2018
 */
@Entity
@Table(name = "MEDIUMWORDS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mediumwords.findAll", query = "SELECT m FROM Mediumwords m"),
    @NamedQuery(name = "Mediumwords.findByMediumWords", query = "SELECT m FROM Mediumwords m WHERE m.mediumWords = :mediumWords"),
    @NamedQuery(name = "Mediumwords.findByFakeMediumWords", query = "SELECT m FROM Mediumwords m WHERE m.fakeMediumWords = :fakeMediumWords")})
public class Mediumwords implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "MEDIUM_WORDS")
    private String mediumWords;
    @Size(max = 11)
    @Column(name = "FAKE_MEDIUM_WORDS")
    private String fakeMediumWords;

    public Mediumwords() {
    }

    public Mediumwords(String mediumWords) {
        this.mediumWords = mediumWords;
    }

    public String getMediumWords() {
        return mediumWords;
    }

    public void setMediumWords(String mediumWords) {
        this.mediumWords = mediumWords;
    }

    public String getFakeMediumWords() {
        return fakeMediumWords;
    }

    public void setFakeMediumWords(String fakeMediumWords) {
        this.fakeMediumWords = fakeMediumWords;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mediumWords != null ? mediumWords.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mediumwords)) {
            return false;
        }
        Mediumwords other = (Mediumwords) object;
        if ((this.mediumWords == null && other.mediumWords != null) || (this.mediumWords != null && !this.mediumWords.equals(other.mediumWords))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Mediumwords[ mediumWords=" + mediumWords + " ]";
    }
    
}
