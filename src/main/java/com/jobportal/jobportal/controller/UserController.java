package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(
            @RequestParam(value = "success", required = false) String success,
            Model model) {

        model.addAttribute("user", new User());

        if (success != null) {
            model.addAttribute("message", "User registered successfully!");
        }

        return "register";
    }


    @PostMapping("/register")
    public String registerUser(User user) {
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }


}
