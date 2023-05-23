package com.cnc.outfit_ecommerce.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
@SqlGroup({
  @Sql(scripts = "file:src/main/resources/db/initial_schema.sql"),
  @Sql(statements = "DROP DATABASE cnc;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public abstract class BaseMysqlContainer {

  private static final MySQLContainer MYSQL_CONTAINER;

  static {
    MYSQL_CONTAINER = new MySQLContainer("mysql:latest").withUsername("root").withPassword("0000");
    MYSQL_CONTAINER.start();
  }

  @DynamicPropertySource
  public static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
  }
}
