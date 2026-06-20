package com.csms.controller;

import com.csms.service.SystemInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/system")
public class AdminSystemController {
    private final SystemInfoService systemInfoService;

    public AdminSystemController(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("systemInfo", systemInfoService.loadSystemInfo());
        return "admin/system/index";
    }

    @PostMapping
    public String update(@RequestParam Map<String, String> params,
                         @RequestParam(required = false) MultipartFile logo,
                         @RequestParam(required = false) MultipartFile cover) {
        Map<String, String> settings = new HashMap<>(params);
        settings.remove("_csrf");
        settings.remove("logo");
        settings.remove("cover");
        systemInfoService.updateSettings(settings, logo, cover);
        return "redirect:/admin/system";
    }
}
