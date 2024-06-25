package com.nemesis.training;

import java.sql.Connection;

public class Main {
  public static void main(String[] args) {
    try {
      String username = args[0];
      if (NameValidator.isValidName(username)) {
        Connection conn = UsersStore.getConnection("application.properties");
        UsersStore usersStore = new UsersStore(conn);
        User user = usersStore.createUser(username);
        if (user.getId() == 0) {
          System.err.println("Failed to save name to database.");
        } else {
          System.out.println("Name added successfully with ID = " + user.getId());
        }
      } else {
        System.out.println(
            "Please, write a name with 8 to 25 characters without capital letters or symbols.");
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("No names were written.");
    }
  }
}
