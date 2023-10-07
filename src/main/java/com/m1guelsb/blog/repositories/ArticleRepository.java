package com.m1guelsb.blog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.m1guelsb.blog.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  @Query("SELECT DISTINCT a FROM Article a JOIN a.categories c WHERE c.title IN :categories")
  Page<Article> findByCategories(List<String> categories, Pageable pageable);

  Optional<Article> findByTitleEqualsIgnoringCase(String title);

}
