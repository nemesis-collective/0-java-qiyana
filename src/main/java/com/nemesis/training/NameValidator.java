package com.nemesis.training;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class NameValidator {

  public static boolean isValidName(final String name) {

    final boolean isValidLength = name.length() >= 8 && name.length() <= 25;

    final String regex = ".*[0-9A-Z\\W_].*";

    final Pattern pattern = Pattern.compile(regex);

    final Matcher matcher = pattern.matcher(name);

    return isValidLength && !matcher.matches();
  }
}
