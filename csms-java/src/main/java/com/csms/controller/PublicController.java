package com.csms.controller;

import com.csms.entity.BookingList;
import com.csms.entity.Inquiry;
import com.csms.service.BookingService;
import com.csms.service.InquiryService;
import com.csms.service.StorageService;
import com.csms.service.SystemInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class PublicController {
    private final StorageService storageService;
    private final BookingService bookingService;
    private final InquiryService inquiryService;
    private final SystemInfoService systemInfoService;

    public PublicController(StorageService storageService, BookingService bookingService, InquiryService inquiryService, SystemInfoService systemInfoService) {
        this.storageService = storageService;
        this.bookingService = bookingService;
        this.inquiryService = inquiryService;
        this.systemInfoService = systemInfoService;
    }

    @ModelAttribute
    public void commonModel(Model model) {
        model.addAttribute("systemInfo", systemInfoService.loadSystemInfo());
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("storages", storageService.findActive());
        return "public/home";
    }

    @GetMapping("/storages")
    public String storages(Model model) {
        model.addAttribute("storages", storageService.findActive());
        return "public/storages";
    }

    @GetMapping("/storages/{id}")
    public String storageDetail(@PathVariable Long id, Model model) {
        model.addAttribute("storage", storageService.findById(id).orElse(null));
        return "public/storage-detail";
    }

    @GetMapping("/booking")
    public String bookingForm(Model model) {
        model.addAttribute("storages", storageService.findActive());
        model.addAttribute("booking", new BookingList());
        return "public/booking";
    }

    @PostMapping("/booking")
    public String submitBooking(@RequestParam Map<String, String> params) {
        bookingService.saveBooking(BookingService.fromForm(params), params);
        return "redirect:/booking?success";
    }

    @GetMapping("/about")
    public String about() {
        return "public/about";
    }

    @GetMapping("/contact")
    public String contactForm() {
        return "public/contact";
    }

    @PostMapping("/contact")
    public String submitContact(@RequestParam Map<String, String> params) {
        Inquiry inquiry = new Inquiry();
        inquiry.setFullname(params.getOrDefault("fullname", ""));
        inquiry.setContact(params.getOrDefault("contact", ""));
        inquiry.setEmail(params.getOrDefault("email", ""));
        inquiry.setMessage(params.getOrDefault("message", ""));
        inquiryService.save(inquiry);
        return "redirect:/contact?success";
    }

    @GetMapping("/programs")
    public String programs() {
        return "public/programs";
    }
}
