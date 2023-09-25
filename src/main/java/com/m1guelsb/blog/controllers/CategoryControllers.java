package com.m1guelsb.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m1guelsb.blog.dtos.CategoryDto;
import com.m1guelsb.blog.services.CategoryServices;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryControllers {

  @Autowired
  private CategoryServices categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> findAll() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(categoryService.findAll());
  }

  @PostMapping
  public ResponseEntity<CategoryDto> create(
      @RequestBody @Valid @NotNull CategoryDto dtoData) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(categoryService.create(dtoData));
  }

}
