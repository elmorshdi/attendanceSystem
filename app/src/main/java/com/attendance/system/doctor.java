package com.attendance.system;

import java.util.ArrayList;

public class doctor {
    private String name ,id,email,passward;
    private ArrayList<subject> subjects=new ArrayList<>();



    public doctor() {
    }
    public doctor(String name, String id, String email, String passward) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.passward = passward;
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

    public ArrayList<subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<subject> subjects) {
        this.subjects = subjects;
    }
}
