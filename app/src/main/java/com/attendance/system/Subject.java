package com.attendance.system;

class Subject {
    private String name, code, drName;
    private long numOfLecture;


    Subject(String code, String name, String drName, long numOfLecture) {
        this.code = code;
        this.name = name;
        this.drName = drName;
        this.numOfLecture = numOfLecture;


    }

    public Subject() {
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getNumOfLecture() {
        return numOfLecture;
    }

    public void setNumOfLecture(long numOfLecture) {
        this.numOfLecture = numOfLecture;
    }
}
