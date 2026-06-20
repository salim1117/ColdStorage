package com.csms.repository;

import com.csms.entity.BookingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingList, Long> {
    Optional<BookingList> findByBookCode(String bookCode);
    List<BookingList> findByStorageId(Long storageId);
    boolean existsByBookCode(String bookCode);
    long countByStatus(Integer status);
}
