package com.m1guelsb.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.m1guelsb.blog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
