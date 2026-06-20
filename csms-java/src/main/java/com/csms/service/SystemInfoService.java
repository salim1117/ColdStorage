package com.csms.service;

import com.csms.entity.SystemInfo;
import com.csms.repository.SystemInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemInfoService {
    private final SystemInfoRepository systemInfoRepository;
    private final FileUploadService fileUploadService;
    private static final String LEGACY_ROOT = "..";

    public SystemInfoService(SystemInfoRepository systemInfoRepository, FileUploadService fileUploadService) {
        this.systemInfoRepository = systemInfoRepository;
        this.fileUploadService = fileUploadService;
    }

    public Map<String, String> loadSystemInfo() {
        Map<String, String> values = new LinkedHashMap<>();
        for (SystemInfo info : systemInfoRepository.findAll()) {
            values.put(info.getMetaField(), info.getMetaValue());
        }

        // Read welcome.html
        try {
            Path welcomePath = Paths.get(LEGACY_ROOT, "welcome.html");
            if (Files.exists(welcomePath)) {
                String welcomeContent = Files.readString(welcomePath, StandardCharsets.UTF_8);
                values.put("welcome", welcomeContent);
            } else {
                values.put("welcome", "");
            }
        } catch (IOException e) {
            values.put("welcome", "");
        }

        // Read about_us.html
        try {
            Path aboutPath = Paths.get(LEGACY_ROOT, "about_us.html");
            if (Files.exists(aboutPath)) {
                String aboutContent = Files.readString(aboutPath, StandardCharsets.UTF_8);
                values.put("about", aboutContent);
            } else {
                values.put("about", "");
            }
        } catch (IOException e) {
            values.put("about", "");
        }

        return values;
    }

    public String get(String field, String fallback) {
        if ("welcome".equals(field)) {
            try {
                Path welcomePath = Paths.get(LEGACY_ROOT, "welcome.html");
                if (Files.exists(welcomePath)) {
                    return Files.readString(welcomePath, StandardCharsets.UTF_8);
                }
            } catch (IOException e) {
                // fallback
            }
            return fallback;
        }
        if ("about".equals(field)) {
            try {
                Path aboutPath = Paths.get(LEGACY_ROOT, "about_us.html");
                if (Files.exists(aboutPath)) {
                    return Files.readString(aboutPath, StandardCharsets.UTF_8);
                }
            } catch (IOException e) {
                // fallback
            }
            return fallback;
        }
        return systemInfoRepository.findByMetaField(field).map(SystemInfo::getMetaValue).orElse(fallback);
    }

    public SystemInfo saveOrUpdate(String field, String value) {
        SystemInfo info = systemInfoRepository.findByMetaField(field).orElseGet(SystemInfo::new);
        info.setMetaField(field);
        info.setMetaValue(value);
        return systemInfoRepository.save(info);
    }

    public void updateSettings(Map<String, String> settings, MultipartFile logo, MultipartFile cover) {
        if (settings != null) {
            for (Map.Entry<String, String> entry : settings.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                if (key == null || val == null) {
                    continue;
                }
                if (key.startsWith("_")) {
                    continue;
                }

                // Write welcome and about us back to files
                if ("welcome".equals(key) || "content[welcome]".equals(key)) {
                    try {
                        Path welcomePath = Paths.get(LEGACY_ROOT, "welcome.html");
                        Files.writeString(welcomePath, val, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                if ("about".equals(key) || "content[about_us]".equals(key)) {
                    try {
                        Path aboutPath = Paths.get(LEGACY_ROOT, "about_us.html");
                        Files.writeString(aboutPath, val, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                saveOrUpdate(key, val);
            }
        }

        if (logo != null && !logo.isEmpty()) {
            String path = fileUploadService.save(logo, "");
            if (path != null) {
                saveOrUpdate("logo", path);
            }
        }

        if (cover != null && !cover.isEmpty()) {
            String path = fileUploadService.save(cover, "");
            if (path != null) {
                saveOrUpdate("cover", path);
            }
        }
    }

    public List<SystemInfo> findAll() {
        return systemInfoRepository.findAll();
    }
}
