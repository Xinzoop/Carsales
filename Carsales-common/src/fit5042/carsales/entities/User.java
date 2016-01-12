/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * User entity
 * @author zipv5_000
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING, length = 16)
@NamedQueries({@NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = User.FIND_BY_ID, query = "SELECT u FROM User u WHERE u.id=:id")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public final static String FIND_BY_EMAIL = "User.findUserByEmail";
    public final static String FIND_BY_ID = "User.findUserById";
    
    /**
     * User groups
     */
    public enum UserGroup {Admin, Salesperson, Customer, Unknown}
    /**
     * User ID number
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    /**
     * Last name
     */
    @Basic(optional = false)
    @Size(min = 0, max = 32)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Must not contain numeric value.")
    private String lastName;
    /**
     * First Name
     */
    @Basic(optional = false)
    @Size(min = 0, max = 32)
    @NotNull(message = "Please enter first name.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Must not contain numeric value.")
    private String firstName;
    /**
     * Email address; also used as user name when logging in
     */
    @Basic(optional = false)
    @Column(unique = true)
    @Size(min = 0, max = 32)
    @NotNull(message = "Please enter the email.")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Must be in a valid email format (e.g. christopher.messom@monash.edu).")
    private String email;
    /**
     * Password
     */
    @Basic(optional = false)
    @Size(min = 0, max = 256)
    private String password;
    /**
     * Address
     */
    @Size(min = 0, max = 256)
    private String address;
    /**
     * Phone number
     */
    @Basic(optional = false)
    @Size(min = 0, max = 16)
    @NotNull(message = "Please enter the phone number.")
    @Pattern(regexp = "^9\\d{7}|0\\d{9}$", message = "Must start with 9 and be 7 digits long, or start with 0 and be 10 digits long.")
    private String phone;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5042.carsale.entities.User[ id=" + id + " ]";
    }
    
}
