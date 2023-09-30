package com.m1guelsb.blog.integration;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractIntegrationTest {
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

  static {
    postgres
        .withUrlParam("serverTimezone", "UTC")
        .withConnectTimeoutSeconds(10000)
        .withReuse(true)
        .start();
  }
}
