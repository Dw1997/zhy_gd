package com.example.zhy_gdapp.beans;

public class Person {
    private String userphone,username,userpass,usertype,useraddr,areaid;

    public Person(String userphone, String username, String userpass, String usertype, String useraddr, String areaid) {
        this.userphone = userphone;
        this.username = username;
        this.userpass = userpass;
        this.usertype = usertype;
        this.useraddr = useraddr;
        this.areaid = areaid;
    }

    public Person(){

    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUseraddr() {
        return useraddr;
    }

    public void setUseraddr(String useraddr) {
        this.useraddr = useraddr;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
}
