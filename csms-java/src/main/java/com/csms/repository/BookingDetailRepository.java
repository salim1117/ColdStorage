package com.csms.repository;

import com.csms.entity.BookingDetail;
import com.csms.entity.key.BookingDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, BookingDetailId> {
    List<BookingDetail> findByBookingId(Long bookingId);

    Optional<BookingDetail> findByBookingIdAndMetaField(Long bookingId, String metaField);

    @Modifying
    @Transactional
    @Query("DELETE FROM BookingDetail bd WHERE bd.bookingId = :bookingId")
    void deleteByBookingId(Long bookingId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BookingDetail bd WHERE bd.bookingId = :bookingId AND bd.metaField = :metaField")
    void deleteByBookingIdAndMetaField(Long bookingId, String metaField);
}
