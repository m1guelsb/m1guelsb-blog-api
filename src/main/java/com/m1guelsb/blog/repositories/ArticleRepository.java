package com.m1guelsb.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.m1guelsb.blog.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}