package com.m1guelsb.blog.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.m1guelsb.blog.dtos.CategoryDto;
import com.m1guelsb.blog.entities.Category;

@Mapper
public interface CategoryMapper {

  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  @InheritInverseConfiguration
  CategoryDto toDto(Category category);

  Category toEntity(CategoryDto categoryDto);

}