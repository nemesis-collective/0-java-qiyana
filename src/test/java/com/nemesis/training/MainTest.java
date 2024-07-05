package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTest {
  Main main = new Main();
  String logpath = "logs/app.log";

  @BeforeEach
  public void tearDown() throws IOException {
    Files.write(
        Paths.get(logpath),
        new byte[0],
        StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.CREATE);
  }

  @Test
  public void RunTest_whenArgsAreValid_mustThrowAddedUserMessage() throws IOException {
    main.run(new String[] {"validname"});
    List<String> logLines = Files.readAllLines(Paths.get(logpath));
    String logContent = String.join("\n", logLines);

    assertFalse(logContent.contains("Failed to save name to database."));
  }

  @Test
  public void RunTest_whenArgsAreVoid_mustNotThrowException() {
    String[] args = {};
    assertDoesNotThrow(
        () -> {
          main.run(args);
        });
  }

  @Test
  public void MainTest_whenArgsIsShortOrLong_mustThrowMessage() throws IOException {

    Main.main(new String[] {"joao"});

    List<String> logLines = Files.readAllLines(Paths.get(logpath));
    String logContent = String.join("\n", logLines);

    assertTrue(
        logContent.contains(
            "Please, write a name with 8 to 25 characters without capital letters or symbols."));
  }
}
