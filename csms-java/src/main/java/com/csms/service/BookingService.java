package com.csms.service;

import com.csms.entity.BookingDetail;
import com.csms.entity.BookingList;
import com.csms.repository.BookingDetailRepository;
import com.csms.repository.BookingRepository;
import com.csms.repository.StorageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final StorageRepository storageRepository;

    public BookingService(BookingRepository bookingRepository, BookingDetailRepository bookingDetailRepository, StorageRepository storageRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingDetailRepository = bookingDetailRepository;
        this.storageRepository = storageRepository;
    }

    public List<BookingList> findAll() {
        return bookingRepository.findAll();
    }

    public Optional<BookingList> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<BookingList> findByStorageId(Long storageId) {
        return bookingRepository.findByStorageId(storageId);
    }

    public long countPending() {
        return bookingRepository.countByStatus(0);
    }

    public long countApproved() {
        return bookingRepository.countByStatus(1);
    }

    public List<BookingDetail> findDetails(Long bookingId) {
        return bookingDetailRepository.findByBookingId(bookingId);
    }

    @Transactional
    public BookingList saveBooking(BookingList booking, Map<String, String> rawFields) {
        if (booking.getBookCode() == null || booking.getBookCode().isBlank()) {
            booking.setBookCode(generateUniqueCode());
        }
        if (booking.getStatus() == null) {
            booking.setStatus(0);
        }
        BookingList saved = bookingRepository.save(booking);

        bookingDetailRepository.deleteByBookingId(saved.getId());
        for (Map.Entry<String, String> entry : normalizeDetails(rawFields, saved).entrySet()) {
            BookingDetail detail = new BookingDetail();
            detail.setBookingId(saved.getId());
            detail.setMetaField(entry.getKey());
            detail.setMetaValue(entry.getValue());
            bookingDetailRepository.save(detail);
        }
        return saved;
    }

    @Transactional
    public void updateStatus(Long id, Integer status) {
        bookingRepository.findById(id).ifPresent(booking -> {
            booking.setStatus(status);
            bookingRepository.save(booking);
        });
    }

    @Transactional
    public void delete(Long id) {
        bookingDetailRepository.deleteByBookingId(id);
        bookingRepository.deleteById(id);
    }

    private String generateUniqueCode() {
        String prefix = "BK-" + YearMonth.now().toString().replace("-", "");
        String code;
        do {
            code = prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (bookingRepository.existsByBookCode(code));
        return code;
    }

    private Map<String, String> normalizeDetails(Map<String, String> rawFields, BookingList booking) {
        Map<String, String> details = new HashMap<>();
        if (rawFields == null) {
            return details;
        }

        for (Map.Entry<String, String> entry : rawFields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key == null || value == null || value.isBlank()) {
                continue;
            }

            if (key.startsWith("_")) {
                continue;
            }
            if (List.of("id", "bookCode", "book_code", "clientName", "client_name", "storageId", "storage_id", "amount", "dateFrom", "date_from", "dateTo", "date_to", "status").contains(key)) {
                continue;
            }
            details.put(key, value);
        }

        details.putIfAbsent("client_name", booking.getClientName());
        details.putIfAbsent("storage_id", String.valueOf(booking.getStorageId()));
        details.putIfAbsent("amount", String.valueOf(booking.getAmount()));
        details.putIfAbsent("date_from", booking.getDateFrom() == null ? "" : booking.getDateFrom().toString());
        details.putIfAbsent("date_to", booking.getDateTo() == null ? "" : booking.getDateTo().toString());
        return details;
    }

    public static BookingList fromForm(Map<String, String> formFields) {
        BookingList booking = new BookingList();
        booking.setClientName(valueOrFallback(formFields, "clientName", joinClientName(formFields)));
        booking.setBookCode(valueOrFallback(formFields, "bookCode", valueOrFallback(formFields, "book_code", null)));

        String storageId = valueOrFallback(formFields, "storageId", valueOrFallback(formFields, "storage_id", null));
        if (storageId != null && !storageId.isBlank()) {
            booking.setStorageId(Long.parseLong(storageId));
        }

        String amount = valueOrFallback(formFields, "amount", valueOrFallback(formFields, "cost", "0"));
        booking.setAmount(Double.parseDouble(amount));

        String dateFrom = valueOrFallback(formFields, "dateFrom", valueOrFallback(formFields, "date_from", null));
        if (dateFrom != null && !dateFrom.isBlank()) {
            booking.setDateFrom(LocalDate.parse(dateFrom));
        }

        String dateTo = valueOrFallback(formFields, "dateTo", valueOrFallback(formFields, "date_to", null));
        if (dateTo != null && !dateTo.isBlank()) {
            booking.setDateTo(LocalDate.parse(dateTo));
        }

        String status = valueOrFallback(formFields, "status", null);
        if (status != null && !status.isBlank()) {
            booking.setStatus(Integer.parseInt(status));
        }
        return booking;
    }

    private static String joinClientName(Map<String, String> formFields) {
        String firstname = valueOrFallback(formFields, "firstname", "");
        String middlename = valueOrFallback(formFields, "middlename", "");
        String lastname = valueOrFallback(formFields, "lastname", "");
        StringBuilder builder = new StringBuilder();
        if (!lastname.isBlank()) {
            builder.append(lastname);
        }
        if (!firstname.isBlank()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(firstname);
        }
        if (!middlename.isBlank()) {
            builder.append(" ").append(middlename);
        }
        return builder.toString().trim();
    }

    private static String valueOrFallback(Map<String, String> map, String key, String fallback) {
        return map != null && map.get(key) != null ? map.get(key) : fallback;
    }
}
