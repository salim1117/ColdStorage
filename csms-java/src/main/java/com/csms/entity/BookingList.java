package com.csms.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_list")
public class BookingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_code", nullable = false, length = 100)
    private String bookCode;

    @Column(name = "client_name", nullable = false, columnDefinition = "TEXT")
    private String clientName;

    @Column(name = "storage_id", nullable = false)
    private Long storageId;

    @Column(nullable = false)
    private Double amount = 0.0;

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    /**
     * status: 0 = Pending, 1 = Approved, 2 = Rejected, 3 = Completed
     */
    @Column(nullable = false)
    private Integer status = 0;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    // Transient helper — resolved from status int
    @Transient
    public String getStatusLabel() {
        return switch (status) {
            case 0 -> "Pending";
            case 1 -> "Approved";
            case 2 -> "Rejected";
            case 3 -> "Completed";
            default -> "Unknown";
        };
    }

    @Transient
    public String getStatusBadgeClass() {
        return switch (status) {
            case 0 -> "warning";
            case 1 -> "success";
            case 2 -> "danger";
            case 3 -> "info";
            default -> "secondary";
        };
    }

    public BookingList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
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

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
