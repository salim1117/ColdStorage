package com.csms.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    private String firstname;

    @Column(columnDefinition = "TEXT")
    private String middlename;

    @Column(nullable = false, length = 250)
    private String lastname;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(columnDefinition = "TEXT")
    private String avatar;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    /**
     * type: 1 = Admin, 2 = Staff
     */
    @Column(nullable = false)
    private Integer type = 0;

    /**
     * status: 0 = not verified, 1 = verified
     */
    @Column(nullable = false)
    private Integer status = 1;

    @CreationTimestamp
    @Column(name = "date_added", nullable = false, updatable = false)
    private LocalDateTime dateAdded;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getFullName() {
        return lastname + ", " + firstname + (middlename != null ? " " + middlename : "");
    }
}
