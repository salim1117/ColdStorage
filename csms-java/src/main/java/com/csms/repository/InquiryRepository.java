package com.csms.repository;

import com.csms.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByStatusOrderByDateCreatedDesc(Integer status);
    long countByStatus(Integer status);
}
