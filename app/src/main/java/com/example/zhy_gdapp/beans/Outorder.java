package com.example.zhy_gdapp.beans;

public class Outorder {
    private String id,uname,uphone,uaddr,gname,gphone,gaddr,time,state;

    public Outorder(String id, String uname, String uphone, String uaddr, String gname, String gphone, String gaddr, String time, String state) {
        this.id = id;
        this.uname = uname;
        this.uphone = uphone;
        this.uaddr = uaddr;
        this.gname = gname;
        this.gphone = gphone;
        this.gaddr = gaddr;
        this.time = time;
        this.state = state;
    }

    public Outorder(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUaddr() {
        return uaddr;
    }

    public void setUaddr(String uaddr) {
        this.uaddr = uaddr;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGphone() {
        return gphone;
    }

    public void setGphone(String gphone) {
        this.gphone = gphone;
    }

    public String getGaddr() {
        return gaddr;
    }

    public void setGaddr(String gaddr) {
        this.gaddr = gaddr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
