package com.nemesis.training;

import lombok.Getter;

@Getter
public class User {

  private final long id;

  private final String name;

  protected User(final long id, final String name) {
    this.id = id;
    this.name = name;
  }
}
