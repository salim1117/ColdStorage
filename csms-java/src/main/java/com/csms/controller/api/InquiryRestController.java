package com.csms.controller.api;

import com.csms.entity.Inquiry;
import com.csms.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API controller for Inquiry (contact messages).
 * Base path: /api/inquiries
 *
 * This controller is additive — it does NOT replace or interfere with
 * the existing AdminInquiryController (Thymeleaf-based).
 */
@RestController
@RequestMapping("/api/inquiries")
public class InquiryRestController {

    private final InquiryService inquiryService;

    public InquiryRestController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    /**
     * GET /api/inquiries
     * Returns all inquiries.
     */
    @GetMapping
    public ResponseEntity<List<Inquiry>> listAll() {
        return ResponseEntity.ok(inquiryService.findAll());
    }

    /**
     * GET /api/inquiries/{id}
     * Returns a single inquiry by ID, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inquiry> getById(@PathVariable Long id) {
        return inquiryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/inquiries
     * Creates a new inquiry (e.g. from an external system or Postman). Returns 201 Created.
     * Status defaults to 0 (unread) if not provided.
     */
    @PostMapping
    public ResponseEntity<Inquiry> create(@RequestBody Inquiry inquiry) {
        inquiry.setId(null);
        if (inquiry.getStatus() == null) {
            inquiry.setStatus(0);
        }
        Inquiry saved = inquiryService.save(inquiry);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * PUT /api/inquiries/{id}
     * Replaces an existing inquiry's editable fields. Returns 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inquiry> update(@PathVariable Long id, @RequestBody Inquiry incoming) {
        return inquiryService.findById(id).map(existing -> {
            incoming.setId(id);
            // Preserve original creation timestamp — not overridden by caller
            Inquiry updated = inquiryService.save(incoming);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * PATCH /api/inquiries/{id}/read
     * Marks a specific inquiry as read (status = 1). Returns 200 OK or 404 if not found.
     */
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markRead(@PathVariable Long id) {
        if (inquiryService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        inquiryService.markRead(id);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE /api/inquiries/{id}
     * Deletes an inquiry. Returns 204 No Content, or 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (inquiryService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        inquiryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
