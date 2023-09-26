package com.m1guelsb.blog.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.m1guelsb.blog.configs.mocks.CategoryMock;
import com.m1guelsb.blog.entities.Category;
import com.m1guelsb.blog.mappers.CategoryMapper;
import com.m1guelsb.blog.repositories.CategoryRepository;
import com.m1guelsb.blog.services.CategoryServices;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CategoryServicesTest {

  CategoryMock input;

  @InjectMocks
  CategoryServices service;

  @Mock
  CategoryRepository repository;

  private CategoryMapper mapper = CategoryMapper.INSTANCE;

  @BeforeEach
  void setUpMocks() throws Exception {
    input = new CategoryMock();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void should_create_Category() {
    Category entity = input.mockEntity(1);
    entity.setId(1L);

    Category persisted = entity;
    persisted.setId(1L);

    when(repository.save(entity)).thenReturn(persisted);

    var result = service.create(mapper.toDto(entity));

    assertNotNull(result);
    assertNotNull(result.id());
    assertNotNull(result.title());
    assertEquals("Category1", result.title());
  }

  @Test
  void should_findAll_Categories() {
    List<Category> list = input.mockEntityList();

    when(repository.findAll()).thenReturn(list);

    var categories = service.findAll();

    assertNotNull(categories);
    assertEquals(5, categories.size());

    var category0 = categories.get(0);
    var category2 = categories.get(2);
    var category4 = categories.get(4);

    assertNotNull(category0);
    assertNotNull(category0.id());
    assertNotNull(category0.title());

    assertEquals("Category0", category0.title());
    assertEquals("Category2", category2.title());
    assertEquals("Category4", category4.title());

  }
}
