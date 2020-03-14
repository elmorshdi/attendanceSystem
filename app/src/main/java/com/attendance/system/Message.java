package com.attendance.system;

public class Message {
    private String id, courseCode, name, reasonOfAbsence;

    public Message() {
    }


    public Message(String id, String name, String courseCode, String reasonOfAbsence) {
        this.id = id;
        this.name = name;
        this.courseCode = courseCode;
        this.reasonOfAbsence = reasonOfAbsence;
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
