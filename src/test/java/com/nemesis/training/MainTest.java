package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
  private final Main main = new Main();
  private final String logpath = "logs/app.log";

  @BeforeEach
  public void tearDown() throws IOException {
    Files.write(
        Paths.get(logpath),
        new byte[0],
        StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.CREATE);
  }

  @Test
  void runTestWhenArgsAreValidMustThrowAddedUserMessage() throws IOException {
    main.run(new String[] {"validname"});
    List<String> logLines = Files.readAllLines(Paths.get(logpath));
    String logContent = String.join("\n", logLines);

    assertFalse(logContent.contains("Failed to save name to database."));
  }

  @Test
  void runTestWhenArgsAreVoidMustNotThrowException() {
    String[] args = {};
    assertDoesNotThrow(
        () -> {
          main.run(args);
        });
  }

  @Test
  void mainTestWhenArgsIsShortOrLongMustThrowMessage() throws IOException {

    Main.main(new String[] {"joao"});

    List<String> logLines = Files.readAllLines(Paths.get(logpath));
    String logContent = String.join("\n", logLines);

    assertTrue(
        logContent.contains(
            "Please, write a name with 8 to 25 characters without capital letters or symbols."));
  }
}
