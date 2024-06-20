package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
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
  void getPropertiesTest_whenParamsValid_shouldReturnProperties() throws IOException {
    Properties properties = config.getProperties("test.application.properties");
    assertEquals("testuser", properties.getProperty("db.username"));
    assertEquals("testpass", properties.getProperty("db.password"));
  }

  @Test
  void getPropertiesTest_whenParamsInvalid_shouldReturnIOException() throws IOException {

    IOException exception =
        assertThrows(
            IOException.class,
            () -> {
              config.getProperties("non-existent.properties");
            });

    assertEquals(
        "Property file 'non-existent.properties' was not found or could not be read.",
        exception.getMessage());
  }
}
