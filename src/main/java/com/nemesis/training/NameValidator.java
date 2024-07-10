package com.nemesis.training;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator {
  public static boolean isValidName(String name) {
    if (name.length() < 8 || name.length() > 25) {
      return false;
    }

    final String regex = ".*[0-9A-Z\\W_].*";

    final Pattern pattern = Pattern.compile(regex);

    final Matcher matcher = pattern.matcher(name);

    return !matcher.matches();
  }
}
