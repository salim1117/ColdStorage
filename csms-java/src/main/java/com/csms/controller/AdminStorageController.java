package com.csms.controller;

import com.csms.entity.Storage;
import com.csms.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/storages")
public class AdminStorageController {
    private final StorageService storageService;

    public AdminStorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("storages", storageService.findAll());
        return "admin/storages/list";
    }

    @GetMapping("/manage")
    public String manage(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("storage", id == null ? new Storage() : storageService.findById(id).orElse(new Storage()));
        return "admin/storages/manage";
    }

    @PostMapping("/manage")
    public String save(Storage storage, @RequestParam(required = false) MultipartFile thumbnail) {
        storageService.save(storage, thumbnail);
        return "redirect:/admin/storages";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        storageService.delete(id);
        return "redirect:/admin/storages";
    }
}
