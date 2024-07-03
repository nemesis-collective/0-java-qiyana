package com.nemesis.training;

import java.sql.Connection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    Main main = new Main();
    main.run(args);
  }

  public void run(String[] args) {
    try {
      String username = args[0];
      if (NameValidator.isValidName(username)) {
        Connection conn = UsersStore.getConnection("application.properties");
        UsersStore usersStore = new UsersStore(conn);
        User user = usersStore.createUser(username);
        usersStore.verifyUserCreation(user);
      } else {
        log.error(
            "Please, write a name with 8 to 25 characters without capital letters or symbols.");
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      log.error("No names were written.");
    }
  }
}
