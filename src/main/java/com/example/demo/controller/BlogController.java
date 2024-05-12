package com.example.demo.controller;

import com.example.demo.model.Blogs;
import com.example.demo.model.User;
import com.example.demo.service.BlogService;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.modeler.BaseAttributeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs/new")
    public String newBlog(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Blogs blog = new Blogs();
        blog.setUser(user);
        model.addAttribute("blog", blog);
//        model.addAttribute("blog", new Blogs());
        return "new_blog";
    }

    @PostMapping("/blogs")
    public String addBlog(@ModelAttribute("blog") Blogs blog,Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        blog.setUser(user);
        blogService.addBlog(blog);
//        User user = (User) session.getAttribute("user");
        List<Blogs> blogs_of_the_user = blogService.getBlogsByUser(user);
        model.addAttribute("blogs", blogs_of_the_user);
        return "blogs_user";
    }

    @GetMapping("/blogs/edit/{id}")
    public String editBlog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getBlog(id));
        return "edit_blog";
    }

    @PostMapping("/blogs/update")
    public String updateBlog(@ModelAttribute("blog") Blogs blog) {
        blogService.editBlog(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect:/blogs";
    }
}