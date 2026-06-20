package com.csms.controller;

import com.csms.service.InquiryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/inquiries")
public class AdminInquiryController {
    private final InquiryService inquiryService;

    public AdminInquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("inquiries", inquiryService.findAll());
        return "admin/inquiries/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        inquiryService.markRead(id);
        model.addAttribute("inquiry", inquiryService.findById(id).orElse(null));
        return "admin/inquiries/detail";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        inquiryService.delete(id);
        return "redirect:/admin/inquiries";
    }
}
