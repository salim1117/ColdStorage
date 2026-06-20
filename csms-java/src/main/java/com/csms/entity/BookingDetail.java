package com.csms.entity;

import jakarta.persistence.*;
import com.csms.entity.key.BookingDetailId;

@Entity
@IdClass(BookingDetailId.class)
@Table(name = "booking_details")
public class BookingDetail {

    @Id
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Id
    @Column(name = "meta_field", nullable = false, columnDefinition = "TEXT")
    private String metaField;

    @Column(name = "meta_value", nullable = false, columnDefinition = "TEXT")
    private String metaValue;

    public BookingDetail() {
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getMetaField() {
        return metaField;
    }

    public void setMetaField(String metaField) {
        this.metaField = metaField;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }
}
