package com.attendance.system;

import java.util.Map;

class Student {
    Map<String, Map<String, String>> subjects;
    private String name, email, id, password, type;

    public Student(String email, String id, String name, String password) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
    }

    public Student(String email, String id, String name, String password, String type, Map<String, Map<String, String>> subjects) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
        this.type = type;
        this.subjects = subjects;
    }

    public Student() {
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

    public Map<String, Map<String, String>> getSubjects() {
        return subjects;
    }

    public void setSubjects(Map<String, Map<String, String>> subjects) {
        this.subjects = subjects;
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
