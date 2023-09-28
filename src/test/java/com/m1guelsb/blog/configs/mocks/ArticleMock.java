package com.m1guelsb.blog.configs.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.m1guelsb.blog.entities.Article;
import com.m1guelsb.blog.dtos.ArticleDto;

public class ArticleMock {

  public Article mockEntity() {
    return mockEntity(0);
  }

  public ArticleDto mockDto() {
    return mockDto(0);
  }

  public List<Article> mockEntityList() {
    List<Article> articles = new ArrayList<Article>();
    for (int i = 0; i < 5; i++) {
      articles.add(mockEntity(i));
    }
    return articles;
  }

  public List<ArticleDto> mockDtoList() {
    List<ArticleDto> articles = new ArrayList<ArticleDto>();
    for (int i = 0; i < 5; i++) {
      articles.add(mockDto(i));
    }
    return articles;
  }

  Article mockEntity(Integer number) {
    CategoryMock categoryMock = new CategoryMock();

    Article article = new Article();
    article.setId(number.longValue());
    article.setTitle("Article" + number);
    article.setBody("<article></article>");
    article.setCategories(new HashSet<>(categoryMock.mockEntityList()));
    article.setCreatedAt(new Date().toInstant());
    article.setUpdatedAt(new Date().toInstant());
    return article;
  }

  ArticleDto mockDto(Integer number) {
    CategoryMock categoryMock = new CategoryMock();

    return new ArticleDto(
        number.longValue(),
        "Article" + number,
        "<article></article>",
        new HashSet<Long>(categoryMock.mockEntityList().stream().map(category -> category.getId()).toList()),
        new Date().toInstant(),
        new Date().toInstant());
  }
}
