package com.m1guelsb.blog.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.m1guelsb.blog.configs.mocks.ArticleMock;
import com.m1guelsb.blog.entities.Article;
import com.m1guelsb.blog.entities.Category;
import com.m1guelsb.blog.exceptions.ResourceNotFoundException;
import com.m1guelsb.blog.repositories.ArticleRepository;
import com.m1guelsb.blog.repositories.CategoryRepository;
import com.m1guelsb.blog.services.ArticleServices;

import com.m1guelsb.blog.dtos.ArticleDto;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ArticleServicesTest {

  ArticleMock articleInput;

  @InjectMocks
  ArticleServices articleService;

  @Mock
  ArticleRepository articleRepository;

  @Mock
  CategoryRepository categoryRepository;

  @BeforeEach
  void setUpMocks() throws Exception {
    articleInput = new ArticleMock();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void should_create_Article_with_Categories() {
    ArticleDto dto = articleInput.mockDto();

    when(categoryRepository.findById(anyLong())).thenAnswer(invocation -> {
      Long categoryId = invocation.getArgument(0);
      boolean isIdValid = dto.categoryIds().stream().map(id -> id == categoryId) != null;
      if (isIdValid) {
        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("Category " + categoryId);
        return Optional.of(category);
      }
      return Optional.empty();
    });

    when(articleRepository.save(any())).thenAnswer(invocation -> {
      Article savedArticle = invocation.getArgument(0);
      savedArticle.setId(1L);
      return savedArticle;
    });

    Article createdArticle = articleService.createArticleWithCategories(dto);

    assertNotNull(createdArticle);
    assertEquals("Article0", createdArticle.getTitle());
    assertEquals("ArticleBrief0", createdArticle.getBrief());
    assertEquals("<article></article>", createdArticle.getBody());
    assertEquals(5, createdArticle.getCategories().size());
  }

  @Test
  void should_findAll_Articles() {
    List<Article> list = articleInput.mockEntityList();
    Pageable pageable = Pageable.ofSize(5).withPage(0);
    Page<Article> personPage = new PageImpl<>(list, pageable, list.size());
    when(articleRepository.findAll(pageable)).thenReturn(personPage);

    var article = articleService.findAll(pageable).getContent();

    var articleOne = article.get(1);
    var articleFour = article.get(4);

    assertNotNull(articleOne);
    assertNotNull(articleOne.getId());
    assertNotNull(articleOne.getTitle());
    assertNotNull(articleOne.getBrief());
    assertNotNull(articleOne.getBody());
    assertNotNull(articleOne.getCreatedAt());
    assertNotNull(articleOne.getUpdatedAt());

    assertEquals("Article1", articleOne.getTitle());
    assertEquals("ArticleBrief1", articleOne.getBrief());
    assertEquals("<article></article>", articleOne.getBody());
    assertEquals("Category2", articleOne.getCategories().stream().toList().get(1).getTitle());

    assertNotNull(articleFour);
    assertNotNull(articleFour.getId());
    assertNotNull(articleFour.getTitle());
    assertNotNull(articleFour.getBody());
    assertNotNull(articleFour.getCreatedAt());
    assertNotNull(articleFour.getUpdatedAt());
    assertEquals("Article4", articleFour.getTitle());
    assertEquals("ArticleBrief4", articleFour.getBrief());
    assertEquals("<article></article>", articleFour.getBody());
    assertEquals("Category3", articleFour.getCategories().stream().toList().get(2).getTitle());
  }

  @Test
  void should_findById_Article_or_throw_NotFound() {
    assertThrows(ResourceNotFoundException.class, () -> articleService.findByIdWithCategories(999L));

    Article entity = articleInput.mockEntity();

    when(articleRepository.findById(any())).thenReturn(Optional.of(entity));

    var article = articleService.findByIdWithCategories(entity.getId());

    assertNotNull(article);
    assertNotNull(article.getId());
    assertNotNull(article.getTitle());
    assertNotNull(article.getBody());
    assertEquals("Article0", article.getTitle());
    assertEquals("<article></article>", article.getBody());
    assertEquals("Category2", article.getCategories().stream().toList().get(1).getTitle());
  }
}