package com.nemesis.training;

import lombok.Getter;
import lombok.Setter;

public class User {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  private String name;

  User(String name) {
    this.name = name;
  }

}
