package com.m1guelsb.blog.configs.mocks;

import java.util.ArrayList;
import java.util.List;

import com.m1guelsb.blog.dtos.CategoryDto;
import com.m1guelsb.blog.entities.Category;

public class CategoryMock {

  public Category mockEntity() {
    return mockEntity(0);
  }

  public CategoryDto mockDto() {
    return mockDto(0);
  }

  public List<Category> mockEntityList() {
    List<Category> categories = new ArrayList<Category>();
    for (int i = 0; i < 5; i++) {
      categories.add(mockEntity(i));
    }
    return categories;
  }

  public Category mockEntity(Integer number) {
    Category category = new Category();
    category.setId(number.longValue());
    category.setTitle("Category" + number);
    return category;
  }

  public CategoryDto mockDto(Integer number) {
    return new CategoryDto(number.longValue(), "Category" + number);
  }
}
