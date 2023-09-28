package com.m1guelsb.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.m1guelsb.blog.dtos.ArticleDto;
import com.m1guelsb.blog.entities.Article;
import com.m1guelsb.blog.services.ArticleServices;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleControllers {

  @Autowired
  private ArticleServices articleServices;

  @GetMapping()
  public ResponseEntity<Page<Article>> findAll(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "sort", defaultValue = "desc") String sortDirection) {
    var sort = "desc".equalsIgnoreCase(sortDirection) ? Direction.DESC : Direction.ASC;

    Pageable pageable = PageRequest.of(page, size, Sort.by(sort, "createdAt"));

    return ResponseEntity.status(HttpStatus.OK)
        .body(articleServices.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Article> findById(
      @PathVariable @NotNull Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(articleServices.findByIdWithCategories(id));
  }

  @PostMapping
  public ResponseEntity<Article> create(
      @RequestBody @Valid @NotNull ArticleDto dtoData) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(articleServices.createArticleWithCategories(dtoData));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Article> update(
      @PathVariable @NotNull Long id,
      @RequestBody @Valid @NotNull ArticleDto dtoData) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(articleServices.updateArticleWithCategories(id, dtoData));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Article> delete(
      @PathVariable @NotNull Long id) {
    articleServices.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
