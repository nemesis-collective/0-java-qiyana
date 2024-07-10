package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConfigTest {
  private static Config config;

  @BeforeAll
  static void setUp() {
    config = Config.getConfig();
  }

  @Test
  void getPropertiesTestWhenParamsValidShouldReturnProperties() {
    final Properties properties = config.getProperties("test.application.properties");
    assertEquals("admin", properties.getProperty("db.USERNAME"));
  }

  @Test
  void getPropertiesTestWhenParamsInvalidShouldNotThrowException() {
    assertDoesNotThrow(
        () -> {
          config.getProperties("non-existent.properties");
        });
  }

  @Test
  void getPropertiesTestWhenPropertiesNameIsVoidShouldReturnVoidProperties() {
    final Properties prop = config.getProperties("");
    assertEquals("{}", prop.toString());
  }
}
