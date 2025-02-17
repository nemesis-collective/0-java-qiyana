package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConfigTest {
  static Config config;

  @BeforeAll
  static void setUp() {
    config = Config.getConfig();
  }

  @Test
  void getPropertiesTest_whenParamsValid_shouldReturnProperties() {
    Properties properties = config.getProperties("test.application.properties");
    assertEquals("admin", properties.getProperty("db.USERNAME"));
    assertEquals("dbtest", properties.getProperty("db.PASSWORD"));
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
