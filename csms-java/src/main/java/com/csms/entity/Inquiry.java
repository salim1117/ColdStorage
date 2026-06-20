package com.csms.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_list")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fullname;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contact;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    /**
     * status: 0 = unread, 1 = read
     */
    @Column(nullable = false)
    private Integer status = 0;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    public Inquiry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
