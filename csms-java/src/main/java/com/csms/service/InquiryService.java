package com.csms.service;

import com.csms.entity.Inquiry;
import com.csms.repository.InquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public List<Inquiry> findAll() {
        return inquiryRepository.findAll();
    }

    public List<Inquiry> findUnread() {
        return inquiryRepository.findByStatusOrderByDateCreatedDesc(0);
    }

    public long countUnread() {
        return inquiryRepository.countByStatus(0);
    }

    public long countRead() {
        return inquiryRepository.countByStatus(1);
    }

    public Optional<Inquiry> findById(Long id) {
        return inquiryRepository.findById(id);
    }

    public Inquiry save(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }

    @Transactional
    public void markRead(Long id) {
        inquiryRepository.findById(id).ifPresent(inquiry -> {
            inquiry.setStatus(1);
            inquiryRepository.save(inquiry);
        });
    }

    public void delete(Long id) {
        inquiryRepository.deleteById(id);
    }
}
