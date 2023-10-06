package com.m1guelsb.blog.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.m1guelsb.blog.dtos.ArticleDto;
import com.m1guelsb.blog.entities.Article;
import com.m1guelsb.blog.entities.Category;
import com.m1guelsb.blog.exceptions.ResourceNotFoundException;
import com.m1guelsb.blog.repositories.ArticleRepository;
import com.m1guelsb.blog.repositories.CategoryRepository;

import com.m1guelsb.blog.specifications.ArticleSpecification;

@Service
public class ArticleServices {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  public Page<Article> findAll(List<String> categories, Pageable pageable) {
    Specification<Article> filters = Specification
        .where(CollectionUtils.isEmpty(categories) ? null : ArticleSpecification.inCategory(categories));

    return articleRepository.findAll(filters, pageable);
  }

  public Article findByIdWithCategories(Long id) {
    return articleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
  }

  public Article createArticleWithCategories(ArticleDto articleDto) {
    Set<Category> categoryList = articleDto.categoryIds().stream()
        .map(id -> categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invalid category id: " + id)))
        .collect(Collectors.toSet());

    Article article = new Article();
    article.setTitle(articleDto.title());
    article.setBrief(articleDto.brief());
    article.setBody(articleDto.body());
    article.setCategories(categoryList);

    return articleRepository.save(article);

  }

  public Article updateArticleWithCategories(Long id, ArticleDto articleDto) {
    return articleRepository.findById(id).map(articleFound -> {
      Set<Category> categoryList = articleDto.categoryIds().stream()
          .map(categoryId -> categoryRepository.findById(categoryId)
              .orElseThrow(() -> new ResourceNotFoundException("Invalid category id: " + categoryId)))
          .collect(Collectors.toSet());

      articleFound.setTitle(articleDto.title());
      articleFound.setBrief(articleDto.brief());
      articleFound.setBody(articleDto.body());
      articleFound.setCategories(categoryList);

      return articleRepository.save(articleFound);
    }).orElseThrow(() -> new ResourceNotFoundException("Article not found"));
  }

  public void delete(Long id) {
    articleRepository.delete(articleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Article does not exists")));
  }
}
