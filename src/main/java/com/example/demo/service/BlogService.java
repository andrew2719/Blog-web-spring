package com.example.demo.service;

import com.example.demo.model.Blogs;
import com.example.demo.model.User;
import com.example.demo.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public void addBlog(Blogs blog) {
        blogRepository.save(blog);
    }

    public Optional<Blogs> getBlog(Long id) {
        return blogRepository.findById(id);
    }

    public void editBlog(Blogs blog) {
        blogRepository.save(blog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public List<Blogs> getBlogsByUser(User user) {
    return blogRepository.findByUser(user);
}
}