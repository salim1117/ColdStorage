package com.csms.controller;

import com.csms.service.SystemInfoService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Injects common model attributes into all admin controllers
 * so the sidebar/navbar can render system name, logo, etc.
 */
@ControllerAdvice(assignableTypes = {
        AdminController.class,
        AdminBookingController.class,
        AdminStorageController.class,
        AdminUserController.class,
        AdminInquiryController.class,
        AdminSystemController.class
})
public class AdminModelAdvice {

    private final SystemInfoService systemInfoService;

    public AdminModelAdvice(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @ModelAttribute
    public void addSystemInfo(Model model) {
        model.addAttribute("systemInfo", systemInfoService.loadSystemInfo());
    }
}
