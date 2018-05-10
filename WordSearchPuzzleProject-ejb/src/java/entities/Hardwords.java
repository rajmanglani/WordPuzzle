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
@Table(name = "HARDWORDS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hardwords.findAll", query = "SELECT h FROM Hardwords h"),
    @NamedQuery(name = "Hardwords.findByHardWords", query = "SELECT h FROM Hardwords h WHERE h.hardWords = :hardWords"),
    @NamedQuery(name = "Hardwords.findByFakeHardWords", query = "SELECT h FROM Hardwords h WHERE h.fakeHardWords = :fakeHardWords")})
public class Hardwords implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "HARD_WORDS")
    private String hardWords;
    @Size(max = 13)
    @Column(name = "FAKE_HARD_WORDS")
    private String fakeHardWords;

    public Hardwords() {
    }

    public Hardwords(String hardWords) {
        this.hardWords = hardWords;
    }

    public String getHardWords() {
        return hardWords;
    }

    public void setHardWords(String hardWords) {
        this.hardWords = hardWords;
    }

    public String getFakeHardWords() {
        return fakeHardWords;
    }

    public void setFakeHardWords(String fakeHardWords) {
        this.fakeHardWords = fakeHardWords;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hardWords != null ? hardWords.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hardwords)) {
            return false;
        }
        Hardwords other = (Hardwords) object;
        if ((this.hardWords == null && other.hardWords != null) || (this.hardWords != null && !this.hardWords.equals(other.hardWords))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Hardwords[ hardWords=" + hardWords + " ]";
    }
    
}
