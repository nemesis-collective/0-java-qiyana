package com.nemesis.training;

public class Main {
  public static void main(String[] args) {
    try {
      User user = new User(-1L, args[0]);
      if (!NameValidator.isValidName(user.name)) {
        System.err.println(
            "Please, write a name with 8 to 25 characters without capital letters or symbols.");
      } else {
        UsersStore usersStore = new UsersStore();
        user.id = usersStore.addUser(user);
      }
      if (user.id != -1) System.out.println("Name added successfully with ID = " + user.id);
      else System.err.println("Failed to save name to database.");
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("No names were written.");
    }
  }
}
