package com.nemesis.training;

import lombok.Generated;
import lombok.Getter;

@Getter
@Generated
public class User {

  private long id;

  private String name;

  protected User(long id, String name) {
    this.id = id;
    this.name = name;
  }
}
