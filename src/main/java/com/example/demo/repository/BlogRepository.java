package com.example.demo.repository;

import com.example.demo.model.Blogs;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blogs, Long> {
    List<Blogs> findByUser(User user);
}