package com.nemesis.training;

import java.sql.Connection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  protected Main() {
    // This constructor is intentionally empty.
  }

  public static void main(final String[] args) {
    final Main main = new Main();
    main.run(args);
  }

  public void run(final String... args) {
    try {
      final String username = args[0];
      if (NameValidator.isValidName(username)) {
        final Connection conn = UsersStore.getConnection("application.properties");
        final UsersStore usersStore = new UsersStore(conn);
        final User user = usersStore.createUser(username);
        usersStore.verifyUserCreation(user.getId());
      } else {
        log.error(
            "Please, write a name with 8 to 25 characters without capital letters or symbols.");
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      log.error("No names were written.");
    }
  }
}
