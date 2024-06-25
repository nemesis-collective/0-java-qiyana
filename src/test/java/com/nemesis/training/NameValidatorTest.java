package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameValidatorTest {

  @Test
  void NameValidatorTest_whenNameIsValid_mustReturnTrue() {
    var response = NameValidator.isValidName("joaopaulo");
    assertTrue(response);
  }

  @Test
  void NameValidatorTest_whenNameIsInvalid_mustReturnFalse() {
    var response = NameValidator.isValidName("João123");
    assertFalse(response);
  }
}
