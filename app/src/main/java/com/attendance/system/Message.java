package com.attendance.system;

public class Message {
    private String id, key, courseCode, name, reasonOfAbsence;

    public Message() {
    }


    public Message(String id, String key, String name, String courseCode, String reasonOfAbsence) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.courseCode = courseCode;
        this.reasonOfAbsence = reasonOfAbsence;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getReasonOfAbsence() {
        return reasonOfAbsence;
    }

    public void setReasonOfAbsence(String reasonOfAbsence) {
        this.reasonOfAbsence = reasonOfAbsence;
    }
}
