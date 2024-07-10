package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameValidatorTest {

  @Test
  void nameValidatorTestWhenNameIsValidMustReturnTrue() {
    assertTrue(NameValidator.isValidName("joaopaulo"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"João123", "joao", "abcdefghijklmnopqrstuvwxyz"})
  void nameValidatorTestWhenNameIsInvalidMustReturnFalse(final String name) {
    assertFalse(NameValidator.isValidName(name));
  }
}
