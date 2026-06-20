package com.csms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {
    private final Path uploadRoot;

    public FileUploadService(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public String save(MultipartFile file, String subDirectory) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            Path targetDir = uploadRoot.resolve(subDirectory == null ? "" : subDirectory).normalize();
            Files.createDirectories(targetDir);

            String originalName = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
            String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String finalName = UUID.randomUUID() + "-" + safeName;
            Path target = targetDir.resolve(finalName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            Path relative = uploadRoot.relativize(target);
            return "uploads/" + relative.toString().replace('\\', '/');
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to store uploaded file", ex);
        }
    }

    public void deleteIfExists(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return;
        }

        Path candidate = Paths.get(relativePath);
        if (!candidate.isAbsolute()) {
            candidate = Paths.get(relativePath.replaceFirst("^uploads/", ""));
            candidate = uploadRoot.resolve(candidate).normalize();
        }

        try {
            Files.deleteIfExists(candidate);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to delete uploaded file", ex);
        }
    }
}
