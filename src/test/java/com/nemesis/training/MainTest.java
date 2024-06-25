package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTest {
  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    outContent.reset();
    errContent.reset();
  }

  @Test
  public void mainTest_whenArgsAreVoid_mustNotThrowException() {
    String[] args = {};

    assertDoesNotThrow(
        () -> {
          Main.main(args);
        });
  }

  @Test
  public void mainTest_whenArgsIsShortOrLong_mustThrowMessage() {
    Main main = new Main();
    main.run(new String[] {"joao"});
    assertEquals(
        "Please, write a name with 8 to 25 characters without capital letters or symbols.",
        outContent.toString());
  }

  @Test
  public void UserCreationTest_whenUserIdIs0_mustThrowErrorMessage() {
    Main main = new Main();
    User userTest = new User(0, "joao");
    main.UserCreation(userTest);
    assertEquals("Failed to save name to database.", errContent.toString());
  }
}
