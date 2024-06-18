package com.nemesis.training;

import java.util.Optional;

public class UserBuild {
    private long id;
    private String name;

    public static UserBuild builder(){
        return new UserBuild();
    }

    public UserBuild id(Optional<Long> id){
        this.id = id.orElse(null);
        return this;
    }

    public UserBuild name(String name){
        this.name = name;
        return this;
    }

    public User build(){
        return new User(id, name);
    }
}
