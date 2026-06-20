package com.csms.service;

import com.csms.entity.Storage;
import com.csms.repository.StorageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    private final StorageRepository storageRepository;
    private final FileUploadService fileUploadService;

    public StorageService(StorageRepository storageRepository, FileUploadService fileUploadService) {
        this.storageRepository = storageRepository;
        this.fileUploadService = fileUploadService;
    }

    public List<Storage> findAll() {
        return storageRepository.findAll();
    }

    public List<Storage> findActive() {
        return storageRepository.findByStatus(1);
    }

    public long countActive() {
        return storageRepository.countByStatus(1);
    }

    public Optional<Storage> findById(Long id) {
        return storageRepository.findById(id);
    }

    @Transactional
    public Storage save(Storage storage, MultipartFile thumbnail) {
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String uploaded = fileUploadService.save(thumbnail, "storages");
            if (uploaded != null) {
                storage.setThumbnailPath(uploaded);
            }
        }
        return storageRepository.save(storage);
    }

    @Transactional
    public void delete(Long id) {
        storageRepository.findById(id).ifPresent(storage -> {
            fileUploadService.deleteIfExists(storage.getThumbnailPath());
            storageRepository.delete(storage);
        });
    }
}
