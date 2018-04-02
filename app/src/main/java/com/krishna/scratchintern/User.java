package com.krishna.scratchintern;

public class User {

  public String id;
  public String name;
  public String email;
  public String password;
  public String mobile;

  public User(String id, String userName, String email, String password, String mobile) {
    this.id = id;
    this.name = userName;
    this.email = email;
    this.password = password;
    this.mobile = mobile;
  }

}
