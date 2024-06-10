package com.nemesis.training;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NameValidatorTest {

    @Test
    void mustReturnTrue(){
        var response = NameValidator.isValidName("joaopaulo");
        assertTrue(response);
    }

    @Test
    void mustReturnFalse(){
        var response = NameValidator.isValidName("João123");
        assertFalse(response);
    }
}
