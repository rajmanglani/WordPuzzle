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
@Table(name = "PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findByEmailAddress", query = "SELECT p FROM Player p WHERE p.emailAddress = :emailAddress"),
    @NamedQuery(name = "Player.findByFirstName", query = "SELECT p FROM Player p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "Player.findByLastName", query = "SELECT p FROM Player p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Player.findByPassword", query = "SELECT p FROM Player p WHERE p.password = :password"),
    @NamedQuery(name = "Player.findByGender", query = "SELECT p FROM Player p WHERE p.gender = :gender"),
    @NamedQuery(name = "Player.findByEasyWordsGiven", query = "SELECT p FROM Player p WHERE p.easyWordsGiven = :easyWordsGiven"),
    @NamedQuery(name = "Player.findByEasyWordsSolved", query = "SELECT p FROM Player p WHERE p.easyWordsSolved = :easyWordsSolved"),
    @NamedQuery(name = "Player.findByEasyPercentage", query = "SELECT p FROM Player p WHERE p.easyPercentage = :easyPercentage"),
    @NamedQuery(name = "Player.findByMediumWordsGiven", query = "SELECT p FROM Player p WHERE p.mediumWordsGiven = :mediumWordsGiven"),
    @NamedQuery(name = "Player.findByMediumWordsSolved", query = "SELECT p FROM Player p WHERE p.mediumWordsSolved = :mediumWordsSolved"),
    @NamedQuery(name = "Player.findByMediumPercentage", query = "SELECT p FROM Player p WHERE p.mediumPercentage = :mediumPercentage"),
    @NamedQuery(name = "Player.findByHardWordsGiven", query = "SELECT p FROM Player p WHERE p.hardWordsGiven = :hardWordsGiven"),
    @NamedQuery(name = "Player.findByHardWordsSolved", query = "SELECT p FROM Player p WHERE p.hardWordsSolved = :hardWordsSolved"),
    @NamedQuery(name = "Player.findByHardPercentage", query = "SELECT p FROM Player p WHERE p.hardPercentage = :hardPercentage")})
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Size(max = 255)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 255)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 15)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 10)
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "EASY_WORDS_GIVEN")
    private Integer easyWordsGiven;
    @Column(name = "EASY_WORDS_SOLVED")
    private Integer easyWordsSolved;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EASY_PERCENTAGE")
    private Double easyPercentage;
    @Column(name = "MEDIUM_WORDS_GIVEN")
    private Integer mediumWordsGiven;
    @Column(name = "MEDIUM_WORDS_SOLVED")
    private Integer mediumWordsSolved;
    @Column(name = "MEDIUM_PERCENTAGE")
    private Double mediumPercentage;
    @Column(name = "HARD_WORDS_GIVEN")
    private Integer hardWordsGiven;
    @Column(name = "HARD_WORDS_SOLVED")
    private Integer hardWordsSolved;
    @Column(name = "HARD_PERCENTAGE")
    private Double hardPercentage;

    public Player() {
    }

    public Player(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEasyWordsGiven() {
        return easyWordsGiven;
    }

    public void setEasyWordsGiven(Integer easyWordsGiven) {
        this.easyWordsGiven = easyWordsGiven;
    }

    public Integer getEasyWordsSolved() {
        return easyWordsSolved;
    }

    public void setEasyWordsSolved(Integer easyWordsSolved) {
        this.easyWordsSolved = easyWordsSolved;
    }

    public Double getEasyPercentage() {
        return easyPercentage;
    }

    public void setEasyPercentage(Double easyPercentage) {
        this.easyPercentage = easyPercentage;
    }

    public Integer getMediumWordsGiven() {
        return mediumWordsGiven;
    }

    public void setMediumWordsGiven(Integer mediumWordsGiven) {
        this.mediumWordsGiven = mediumWordsGiven;
    }

    public Integer getMediumWordsSolved() {
        return mediumWordsSolved;
    }

    public void setMediumWordsSolved(Integer mediumWordsSolved) {
        this.mediumWordsSolved = mediumWordsSolved;
    }

    public Double getMediumPercentage() {
        return mediumPercentage;
    }

    public void setMediumPercentage(Double mediumPercentage) {
        this.mediumPercentage = mediumPercentage;
    }

    public Integer getHardWordsGiven() {
        return hardWordsGiven;
    }

    public void setHardWordsGiven(Integer hardWordsGiven) {
        this.hardWordsGiven = hardWordsGiven;
    }

    public Integer getHardWordsSolved() {
        return hardWordsSolved;
    }

    public void setHardWordsSolved(Integer hardWordsSolved) {
        this.hardWordsSolved = hardWordsSolved;
    }

    public Double getHardPercentage() {
        return hardPercentage;
    }

    public void setHardPercentage(Double hardPercentage) {
        this.hardPercentage = hardPercentage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailAddress != null ? emailAddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.emailAddress == null && other.emailAddress != null) || (this.emailAddress != null && !this.emailAddress.equals(other.emailAddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Player[ emailAddress=" + emailAddress + " ]";
    }
    
}
