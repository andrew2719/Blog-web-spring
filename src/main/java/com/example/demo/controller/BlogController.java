package com.example.demo.controller;

import com.example.demo.model.Blogs;
import com.example.demo.model.User;
import com.example.demo.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.modeler.BaseAttributeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

//    @PostMapping("/blogs")
//    public String addBlog(@ModelAttribute("blog") Blogs blog,Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        blog.setUser(user);
//        blogService.addBlog(blog);
////        User user = (User) session.getAttribute("user");
//        List<Blogs> blogs_of_the_user = blogService.getBlogsByUser(user);
//        model.addAttribute("blogs", blogs_of_the_user);
//        return "blogs_user";
//    }
    @RequestMapping(value = "/blogs", method = {RequestMethod.GET, RequestMethod.POST})
    public String manageBlogs(@ModelAttribute("blog") Blogs blog, BindingResult result, Model model, HttpSession session, HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("POST") && !result.hasErrors()) {
            User user = (User) session.getAttribute("user");
            blog.setUser(user);
            blogService.addBlog(blog);
            return "redirect:/blogs";  // Redirect to avoid duplicate form submission
        } else {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                List<Blogs> blogs = blogService.getBlogsByUser(user);
                model.addAttribute("blogs", blogs);
            }
            return "blogs_user";  // This view shows the blogs or the form to add new blogs
        }
    }


    @GetMapping("/blogs/edit/{id}")
    public String editBlog(@PathVariable Long id, Model model) {
        Optional<Blogs> blog = blogService.getBlog(id);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
        } else {
            return "redirect:/blogs";  // Redirect if the blog is not found
        }
        return "edit_blog";  // Name of the HTML file
    }


    @PostMapping("/blogs/update")
    public String updateBlog(@ModelAttribute("blog") Blogs blog, HttpSession session) {
        User user = (User) session.getAttribute("user");
        blog.setUser(user);
        blogService.editBlog(blog);  // Save changes to the database
        return "redirect:/blogs";  // Redirect to avoid duplicate submissions
    }


    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect:/blogs";
    }
}