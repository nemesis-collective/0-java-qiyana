package com.nemesis.training;

public class Main {
  public static void main(String[] args) {
    if (args.length < 1)
      System.out.println("Please, enter a name, it needs to have at least 8 characters.");
    String name = args[0];
    int id = -1;
    if (!NameValidator.isValidName(name))
      System.out.println("Please, enter a name with at least 8 characters.");
    else id = UserStore.addName(name);

    if (id != -1) System.out.println("Name added successfully with ID = " + id);
    else System.err.println("Failed to save name to database");
  }
}
