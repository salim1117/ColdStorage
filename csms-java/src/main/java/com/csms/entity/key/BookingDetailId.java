package com.csms.entity.key;

import java.io.Serializable;
import java.util.Objects;

public class BookingDetailId implements Serializable {
    private Long bookingId;
    private String metaField;

    public BookingDetailId() {
    }

    public BookingDetailId(Long bookingId, String metaField) {
        this.bookingId = bookingId;
        this.metaField = metaField;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookingDetailId that = (BookingDetailId) o;
        return Objects.equals(bookingId, that.bookingId) && Objects.equals(metaField, that.metaField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, metaField);
    }
}
