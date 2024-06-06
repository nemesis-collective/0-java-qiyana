package com.nemesis.training;

import lombok.Getter;

@Getter
public class User {

  private Long id;

  private String name;

  User(String name) {
    this.name = name;
  }

}
