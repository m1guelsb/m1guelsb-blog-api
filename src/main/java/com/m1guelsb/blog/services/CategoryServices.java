package com.m1guelsb.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.m1guelsb.blog.dtos.CategoryDto;
import com.m1guelsb.blog.mappers.CategoryMapper;
import com.m1guelsb.blog.repositories.CategoryRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
@Validated
public class CategoryServices {
  @Autowired
  CategoryRepository categoryRepository;

  private CategoryMapper mapper = CategoryMapper.INSTANCE;

  public List<CategoryDto> findAll() {
    return categoryRepository
        .findAll()
        .stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  public CategoryDto create(@Valid @NotNull CategoryDto dtoData) {
    return mapper.toDto(categoryRepository
        .save(mapper.toEntity(dtoData)));
  }
}
