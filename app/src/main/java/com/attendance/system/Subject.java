package com.attendance.system;

class Subject {
    private String name, code, drId;
    private long numOfLecture;


    Subject(String code, String name, String drId, long numOfLecture) {
        this.code = code;
        this.name = name;
        this.drId = drId;
        this.numOfLecture = numOfLecture;


    }

    public Subject() {
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
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
