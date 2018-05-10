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
@Table(name = "EASYWORDS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Easywords.findAll", query = "SELECT e FROM Easywords e"),
    @NamedQuery(name = "Easywords.findByEasyWords", query = "SELECT e FROM Easywords e WHERE e.easyWords = :easyWords"),
    @NamedQuery(name = "Easywords.findByFakeEasyWords", query = "SELECT e FROM Easywords e WHERE e.fakeEasyWords = :fakeEasyWords")})
public class Easywords implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "EASY_WORDS")
    private String easyWords;
    @Size(max = 9)
    @Column(name = "FAKE_EASY_WORDS")
    private String fakeEasyWords;

    public Easywords() {
    }

    public Easywords(String easyWords) {
        this.easyWords = easyWords;
    }

    public String getEasyWords() {
        return easyWords;
    }

    public void setEasyWords(String easyWords) {
        this.easyWords = easyWords;
    }

    public String getFakeEasyWords() {
        return fakeEasyWords;
    }

    public void setFakeEasyWords(String fakeEasyWords) {
        this.fakeEasyWords = fakeEasyWords;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (easyWords != null ? easyWords.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Easywords)) {
            return false;
        }
        Easywords other = (Easywords) object;
        if ((this.easyWords == null && other.easyWords != null) || (this.easyWords != null && !this.easyWords.equals(other.easyWords))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Easywords[ easyWords=" + easyWords + " ]";
    }
    
}
