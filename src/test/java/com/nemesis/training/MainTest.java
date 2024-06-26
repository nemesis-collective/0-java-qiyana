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
  public void RunTest_whenArgsAreValid_mustThrowAddedUserMessage() {
    String[] args = {"validname"};
    Main main = new Main();
    main.run(args);
    assertNotEquals("Failed to save name to database.", outContent.toString());
  }

  @Test
  public void RunTest_whenArgsAreVoid_mustNotThrowException() {
    String[] args = {};
    Main mainTest = new Main();
    assertDoesNotThrow(
        () -> {
          mainTest.run(args);
        });
  }

  @Test
  public void RunTest_whenArgsIsShortOrLong_mustThrowMessage() {
    Main main = new Main();
    main.run(new String[] {"joao"});
    assertEquals(
        "Please, write a name with 8 to 25 characters without capital letters or symbols.",
        outContent.toString());
  }
}
