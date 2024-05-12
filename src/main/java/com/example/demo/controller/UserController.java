package com.example.demo.controller;

import com.example.demo.model.Blogs;
import com.example.demo.service.BlogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.demo.model.User;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private BlogService blogService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") User user) {
        userService.registerNewUserAccount(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);  // Set user in the session
            return "redirect:/home";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("userId", user.getId());
            return "home";
        }
        return "redirect:/login";

    }
//    @GetMapping("/home")
//    public String home(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user != null) {
//            List<Blogs> blogs = blogService.getBlogsByUser(user);
//            model.addAttribute("username", user.getUsername());
//            model.addAttribute("userId", user.getId());
//            model.addAttribute("blogs", blogs);
//            return "home";
//        }
//        return "redirect:/login";
//    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers(); // Assuming you have this method in your UserService
        model.addAttribute("users", users);
        return "users"; // This is the name of your Thymeleaf template
    }
}

