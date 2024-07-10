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
  void getPropertiesTest_whenParamsValid_shouldReturnProperties() {
    Properties properties = config.getProperties("test.application.properties");
    assertEquals("admin", properties.getProperty("db.USERNAME"));
  }

  @Test
  void getPropertiesTest_whenParamsInvalid_shouldNotThrowException() {
    assertDoesNotThrow(
        () -> {
          config.getProperties("non-existent.properties");
        });
  }

  @Test
  void getPropertiesTest_whenPropertiesNameIsVoid_shouldReturnVoidProperties() {
    Properties prop = config.getProperties("");
    assertEquals("{}", prop.toString());
  }
}
