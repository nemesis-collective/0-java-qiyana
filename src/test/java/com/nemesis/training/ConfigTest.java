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
    Properties properties = config.getProperties("testPath");
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
    Properties prop = config.getProperties("");
    assertEquals("{}", prop.toString());
  }
}
