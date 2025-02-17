package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NameValidatorTest {

  @Test
  void NameValidatorTest_whenNameIsValid_mustReturnTrue() {
    var response = NameValidator.isValidName("joaopaulo");
    assertTrue(response);
  }

  @ParameterizedTest
  @ValueSource(strings = {"João123", "joao", "abcdefghijklmnopqrstuvwxyz"})
  void NameValidatorTest_whenNameIsInvalid_mustReturnFalse(String name) {
    var response = NameValidator.isValidName(name);
    assertFalse(response);
  }
}
