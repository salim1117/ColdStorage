package com.csms.controller.api;

import com.csms.entity.Storage;
import com.csms.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API controller for Storage units.
 * Base path: /api/storages
 *
 * This controller is additive — it does NOT replace or interfere with
 * the existing AdminStorageController (Thymeleaf-based).
 */
@RestController
@RequestMapping("/api/storages")
public class StorageRestController {

    private final StorageService storageService;

    public StorageRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * GET /api/storages
     * Returns all storage units.
     */
    @GetMapping
    public ResponseEntity<List<Storage>> listAll() {
        return ResponseEntity.ok(storageService.findAll());
    }

    /**
     * GET /api/storages/{id}
     * Returns a single storage unit by ID, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Storage> getById(@PathVariable Long id) {
        return storageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/storages
     * Creates a new storage unit. Returns 201 Created with the saved entity.
     * Note: thumbnail upload is not supported via JSON REST API — use the admin UI for that.
     */
    @PostMapping
    public ResponseEntity<Storage> create(@RequestBody Storage storage) {
        // Clear id to ensure insert, not update
        storage.setId(null);
        Storage saved = storageService.save(storage, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * PUT /api/storages/{id}
     * Updates an existing storage unit. Returns 404 if the id does not exist.
     * Note: thumbnail is not updated via this endpoint — use the admin UI.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Storage> update(@PathVariable Long id, @RequestBody Storage incoming) {
        return storageService.findById(id).map(existing -> {
            incoming.setId(id);
            // Preserve the existing thumbnail path — REST PUT does not overwrite it
            if (incoming.getThumbnailPath() == null) {
                incoming.setThumbnailPath(existing.getThumbnailPath());
            }
            Storage updated = storageService.save(incoming, null);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/storages/{id}
     * Deletes a storage unit and its associated thumbnail file. Returns 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (storageService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        storageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
