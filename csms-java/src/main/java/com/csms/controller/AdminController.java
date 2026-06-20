package com.csms.controller;

import com.csms.service.BookingService;
import com.csms.service.InquiryService;
import com.csms.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    private final StorageService storageService;
    private final BookingService bookingService;
    private final InquiryService inquiryService;

    public AdminController(StorageService storageService, BookingService bookingService, InquiryService inquiryService) {
        this.storageService = storageService;
        this.bookingService = bookingService;
        this.inquiryService = inquiryService;
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("activeStorageCount", storageService.countActive());
        model.addAttribute("pendingBookingCount", bookingService.countPending());
        model.addAttribute("approvedBookingCount", bookingService.countApproved());
        model.addAttribute("unreadInquiryCount", inquiryService.countUnread());
        model.addAttribute("readInquiryCount", inquiryService.countRead());
        return "admin/dashboard";
    }

    @GetMapping("/admin/login")
    public String login() {
        return "admin/login";
    }
}
