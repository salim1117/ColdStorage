package com.csms.controller.api;

import com.csms.entity.SystemInfo;
import com.csms.repository.SystemInfoRepository;
import com.csms.service.SystemInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST API controller for system settings (SystemInfo key-value store).
 * Base path: /api/system-info
 *
 * This controller is additive — it does NOT replace or interfere with
 * the existing AdminSystemController (Thymeleaf-based).
 */
@RestController
@RequestMapping("/api/system-info")
public class SystemInfoRestController {

    private final SystemInfoService systemInfoService;
    private final SystemInfoRepository systemInfoRepository;

    public SystemInfoRestController(SystemInfoService systemInfoService,
                                    SystemInfoRepository systemInfoRepository) {
        this.systemInfoService = systemInfoService;
        this.systemInfoRepository = systemInfoRepository;
    }

    /**
     * GET /api/system-info
     * Returns all system settings as a flat field → value map.
     * Note: 'welcome' and 'about' fields are read from the filesystem HTML files.
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> getAll() {
        return ResponseEntity.ok(systemInfoService.loadSystemInfo());
    }

    /**
     * GET /api/system-info/{field}
     * Returns the value of a single system info field, or 404 if the field does not exist.
     */
    @GetMapping("/{field}")
    public ResponseEntity<Map<String, String>> getField(@PathVariable String field) {
        String value = systemInfoService.get(field, null);
        if (value == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("field", field, "value", value));
    }

    /**
     * PUT /api/system-info/{field}
     * Upserts (create or update) a system info field value.
     * Request body: { "value": "..." }
     * Returns 200 OK with the saved entity.
     */
    @PutMapping("/{field}")
    public ResponseEntity<SystemInfo> upsertField(
            @PathVariable String field,
            @RequestBody Map<String, String> body) {
        String value = body.get("value");
        if (value == null) {
            return ResponseEntity.badRequest().build();
        }
        SystemInfo saved = systemInfoService.saveOrUpdate(field, value);
        return ResponseEntity.ok(saved);
    }

    /**
     * DELETE /api/system-info/{field}
     * Deletes a system info field by its field name. Returns 204 No Content, or 404 if not found.
     */
    @DeleteMapping("/{field}")
    public ResponseEntity<Void> deleteField(@PathVariable String field) {
        var infoOpt = systemInfoRepository.findByMetaField(field);
        if (infoOpt.isEmpty()) {
            return ResponseEntity.<Void>notFound().build();
        }
        systemInfoRepository.delete(infoOpt.get());
        return ResponseEntity.<Void>noContent().build();
    }
}
