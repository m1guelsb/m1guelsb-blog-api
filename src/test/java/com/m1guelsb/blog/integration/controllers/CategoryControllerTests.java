package com.m1guelsb.blog.integration.controllers;

import org.junit.jupiter.api.TestMethodOrder;
import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.m1guelsb.blog.configs.TestConfigs;
import com.m1guelsb.blog.configs.mocks.CategoryMock;
import com.m1guelsb.blog.dtos.CategoryDto;
import com.m1guelsb.blog.entities.Category;
import com.m1guelsb.blog.integration.AbstractIntegrationTest;
import com.m1guelsb.blog.mappers.CategoryMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
public class CategoryControllerTests extends AbstractIntegrationTest {
  private static RequestSpecification specification;
  private static CategoryMapper mapper;

  private static CategoryMock categoryMock = new CategoryMock();

  @BeforeAll
  public static void setup() {
    mapper = CategoryMapper.INSTANCE;
  }

  @Test
  @Order(0)
  public void should_create_Category() throws JsonMappingException, JsonProcessingException {
    CategoryDto categoryDto = categoryMock.mockDto();

    specification = new RequestSpecBuilder()
        .setBasePath("/api/v1/categories")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    var result = given().spec(specification)
        .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_WEB_DEV)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(categoryDto)
        .port(TestConfigs.SERVER_PORT)
        .when()
        .post()
        .then()
        .statusCode(201)
        .extract()
        .body();

    CategoryDto createdCategory = mapper.toDto((Category) result);

    assertNotNull(createdCategory.id());
    assertNotNull(createdCategory.title());

    assertEquals("Category0", createdCategory.title());
  }
}
