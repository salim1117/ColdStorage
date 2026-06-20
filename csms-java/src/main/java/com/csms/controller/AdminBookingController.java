package com.csms.controller;

import com.csms.entity.BookingDetail;
import com.csms.entity.BookingList;
import com.csms.entity.Storage;
import com.csms.service.BookingService;
import com.csms.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/bookings")
public class AdminBookingController {
    private final BookingService bookingService;
    private final StorageService storageService;

    public AdminBookingController(BookingService bookingService, StorageService storageService) {
        this.bookingService = bookingService;
        this.storageService = storageService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        Map<Long, String> storageNames = storageService.findAll().stream()
                .collect(Collectors.toMap(Storage::getId, Storage::getName, (a, b) -> a));
        model.addAttribute("storageNames", storageNames);
        return "admin/bookings/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        BookingList booking = bookingService.findById(id).orElse(null);
        model.addAttribute("booking", booking);
        if (booking != null) {
            Storage storage = storageService.findById(booking.getStorageId()).orElse(null);
            model.addAttribute("storage", storage);
        }
        
        java.util.List<BookingDetail> detailsList = bookingService.findDetails(id);
        Map<String, String> metaMap = detailsList.stream()
                .collect(Collectors.toMap(BookingDetail::getMetaField, BookingDetail::getMetaValue, (a, b) -> a));
        model.addAttribute("meta", metaMap);
        model.addAttribute("details", detailsList);
        return "admin/bookings/detail";
    }

    @PostMapping("/status")
    public String updateStatus(@RequestParam Long id, @RequestParam Integer status) {
        bookingService.updateStatus(id, status);
        return "redirect:/admin/bookings/" + id;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        bookingService.delete(id);
        return "redirect:/admin/bookings";
    }
}
