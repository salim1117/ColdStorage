package com.csms.service;

import com.csms.entity.User;
import com.csms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadService fileUploadService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileUploadService fileUploadService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileUploadService = fileUploadService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findVerified() {
        return userRepository.findByStatus(1);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User save(User user, String rawPassword, MultipartFile avatar) {
        boolean editingExisting = user.getId() != null;
        User persisted = editingExisting ? userRepository.findById(user.getId()).orElse(new User()) : new User();

        if (user.getId() == null || user.getFirstname() != null) {
            persisted.setFirstname(user.getFirstname());
            persisted.setMiddlename(user.getMiddlename());
            persisted.setLastname(user.getLastname());
            persisted.setUsername(user.getUsername());
            persisted.setType(user.getType());
            persisted.setStatus(user.getStatus());
        }

        if (avatar != null && !avatar.isEmpty()) {
            String uploaded = fileUploadService.save(avatar, "avatars");
            if (uploaded != null) {
                persisted.setAvatar(uploaded);
            }
        }

        if (rawPassword != null && !rawPassword.isBlank()) {
            persisted.setPassword(passwordEncoder.encode(rawPassword));
        } else if (persisted.getId() == null && (persisted.getPassword() == null || persisted.getPassword().isBlank())) {
            persisted.setPassword(passwordEncoder.encode("change-me"));
        }

        return userRepository.save(persisted);
    }

    @Transactional
    public void changePassword(Long id, String rawPassword) {
        userRepository.findById(id).ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(rawPassword));
            userRepository.save(user);
        });
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            fileUploadService.deleteIfExists(user.getAvatar());
            userRepository.delete(user);
        });
    }
}
