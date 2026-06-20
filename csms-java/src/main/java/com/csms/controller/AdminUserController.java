package com.csms.controller;

import com.csms.entity.User;
import com.csms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users/list";
    }

    @GetMapping("/manage")
    public String manage(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("user", id == null ? new User() : userService.findById(id).orElse(new User()));
        return "admin/users/manage";
    }

    @PostMapping("/manage")
    public String save(User user, @RequestParam(required = false) String password, @RequestParam(required = false) MultipartFile avatar) {
        userService.save(user, password, avatar);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
