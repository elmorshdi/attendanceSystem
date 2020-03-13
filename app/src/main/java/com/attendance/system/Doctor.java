package com.attendance.system;

import java.util.ArrayList;

public class Doctor {
    private String name, id, email, password;
    private ArrayList<Subject> Subjects = new ArrayList<>();


    public Doctor() {
    }

    Doctor(String name, String id, String email, String password) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Subject> getSubjects() {
        return Subjects;
    }

    public void setSubjects(ArrayList<Subject> Subjects) {
        this.Subjects = Subjects;
    }
}
