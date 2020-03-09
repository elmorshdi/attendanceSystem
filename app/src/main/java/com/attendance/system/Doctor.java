package com.attendance.system;

import java.util.ArrayList;

public class Doctor {
    private String name, id, email, passward, type;
    private ArrayList<Subject> Subjects = new ArrayList<>();


    public Doctor() {
    }

    public Doctor(String name, String id, String email, String passward) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.passward = passward;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public ArrayList<Subject> getSubjects() {
        return Subjects;
    }

    public void setSubjects(ArrayList<Subject> Subjects) {
        this.Subjects = Subjects;
    }
}