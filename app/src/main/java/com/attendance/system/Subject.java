package com.attendance.system;

class Subject {
    private String name, code, drname;
    private long numoflecture;


    Subject(String code, String name, String drname, long numoflecture) {
        this.code = code;
        this.name = name;
        this.drname = drname;
        this.numoflecture = numoflecture;


    }

    public String getDrname() {
        return drname;
    }
    public void setDrname(String drname) {
        this.drname = drname;
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

    public Subject() {
    }

    public long getNumoflecture() {
        return numoflecture;
    }

    public void setNumoflecture(long numoflecture) {
        this.numoflecture = numoflecture;
    }
}
