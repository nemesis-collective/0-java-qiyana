package com.nemesis.training;

public class Main {
  public static void main(String[] args) {
    try {
      String username = args[0];
      if (!NameValidator.isValidName(username)) {
        throw new Exception(
            "Please, write a name with 8 to 25 characters without capital letters or symbols.");
      }

      UsersStore usersStore = new UsersStore();
      usersStore.addUser(username);

    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("No names were written.");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
