package com.csms.controller.api;

import com.csms.entity.BookingDetail;
import com.csms.entity.BookingList;
import com.csms.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * REST API controller for Bookings.
 * Base path: /api/bookings
 *
 * This controller is additive — it does NOT replace or interfere with
 * the existing AdminBookingController (Thymeleaf-based).
 *
 * Notes on BookingService integration:
 *  - saveBooking(booking, rawFields) is used for create/update; rawFields is passed as an
 *    empty map when called from the REST layer (extra key-value detail fields not supported via JSON body).
 *  - BookingDetail sub-resources are exposed at /api/bookings/{id}/details.
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    private final BookingService bookingService;

    public BookingRestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * GET /api/bookings
     * Returns all bookings.
     */
    @GetMapping
    public ResponseEntity<List<BookingList>> listAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    /**
     * GET /api/bookings/{id}
     * Returns a single booking by ID, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookingList> getById(@PathVariable Long id) {
        return bookingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/bookings/{id}/details
     * Returns all BookingDetail (meta) records for the given booking ID.
     * Returns an empty list if the booking has no details (not 404).
     */
    @GetMapping("/{id}/details")
    public ResponseEntity<List<BookingDetail>> getDetails(@PathVariable Long id) {
        if (bookingService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingService.findDetails(id));
    }

    /**
     * POST /api/bookings
     * Creates a new booking. A unique book code is auto-generated if not provided.
     * Status defaults to 0 (Pending) if not specified.
     * Returns 201 Created with the saved booking.
     *
     * Extra detail fields (beyond the core booking fields) are not supported via
     * the JSON body — use the admin UI form for that.
     */
    @PostMapping
    public ResponseEntity<BookingList> create(@RequestBody BookingList booking) {
        booking.setId(null);
        BookingList saved = bookingService.saveBooking(booking, Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * PUT /api/bookings/{id}
     * Updates an existing booking's core fields. Returns 404 if not found.
     * The bookCode is preserved from the existing record (not overwritten by caller).
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookingList> update(@PathVariable Long id,
                                              @RequestBody BookingList incoming) {
        return bookingService.findById(id).map(existing -> {
            incoming.setId(id);
            // Preserve the auto-generated book code — never let caller change it via REST
            incoming.setBookCode(existing.getBookCode());
            BookingList updated = bookingService.saveBooking(incoming, Collections.emptyMap());
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/bookings/{id}/status
     * Updates only the status of a booking.
     * Request body: { "status": 1 }
     * Status values: 0=Pending, 1=Approved, 2=Rejected, 3=Completed
     * Returns 200 OK or 404 if not found.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestBody Map<String, Integer> body) {
        if (bookingService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Integer status = body.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }
        bookingService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE /api/bookings/{id}
     * Deletes a booking and all its associated detail records. Returns 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (bookingService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
