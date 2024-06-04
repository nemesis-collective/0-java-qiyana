package com.nemesis.training;

public class NameValidator {
  public static boolean isValidName(String name) {
    if (name.length() < 8 || name.length() > 25) {
      return false;
    }
    String validName = name.toLowerCase();
    return validName.equals(name);
  }
}
