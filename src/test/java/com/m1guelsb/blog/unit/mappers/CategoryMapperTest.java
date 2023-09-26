package com.m1guelsb.blog.unit.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.m1guelsb.blog.configs.mocks.CategoryMock;
import com.m1guelsb.blog.dtos.CategoryDto;
import com.m1guelsb.blog.entities.Category;
import com.m1guelsb.blog.mappers.CategoryMapper;

class CategoryMapperTest {

  CategoryMock inputObject;

  private CategoryMapper mapper = CategoryMapper.INSTANCE;

  @BeforeEach
  public void setUp() {
    inputObject = new CategoryMock();
  }

  @Test
  void should_parse_dto_and_entity() {
    CategoryDto dto = mapper.toDto(inputObject.mockEntity());
    assertEquals(Long.valueOf(0L), dto.id());
    assertEquals("Category0", dto.title());

    Category output = mapper.toEntity(dto);
    assertEquals(Long.valueOf(0L), output.getId());
    assertEquals("Category0", output.getTitle());
  }
}
