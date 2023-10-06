package com.m1guelsb.blog.specifications;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.m1guelsb.blog.entities.Article;

public class ArticleSpecification {

  private ArticleSpecification() {
  }

  public static Specification<Article> inCategory(List<String> categories) {
    return (root, query, builder) -> root.get("categories")
        .in(categories);
  }
}
