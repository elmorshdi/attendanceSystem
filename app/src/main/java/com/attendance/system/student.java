package com.attendance.system;

import java.util.ArrayList;

class student {
    private String name, email, id, password, type;
  private ArrayList<subject>subjects=new ArrayList<>();

    public student(String name, String email, String id, String password, String type) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<subject> subjects) {
        this.subjects = subjects;
    }

    public student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
