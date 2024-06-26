package com.nemesis.training;

import java.sql.Connection;

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
        VerifyUserCreation(user);
      } else {
        System.out.print(
            "Please, write a name with 8 to 25 characters without capital letters or symbols.");
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.print("No names were written.");
    }
  }

  public void VerifyUserCreation(User user) {
    if (user.getId() == 0) {
      System.err.print("Failed to save name to database.");
    } else {
      System.out.print("Name added successfully with ID = " + user.getId());
    }
  }
}
