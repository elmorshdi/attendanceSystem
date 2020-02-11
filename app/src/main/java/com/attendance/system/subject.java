package com.attendance.system;

class subject {
    String name ,code, drname,numoflecture;



    public subject(String name, String code, String drname, String numoflecture) {
        this.name = name;
        this.code = code;
        this.numoflecture = numoflecture;
        this.drname = drname;

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

    public String getNumoflecture() {
        return numoflecture;
    }
    public void setNumoflecture(String numoflecture) {
        this.numoflecture = numoflecture;
    }

    public subject() {
    }
}
