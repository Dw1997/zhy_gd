package com.example.zhy_gdapp.beans;

public class Orders {
    private String orderid;
    private String getuser;
    private String poster;
    private String state;
    private String getpost;
    private String timee;

    public Orders(){
    }

    public Orders(String orderid, String getuser, String poster, String state, String getpost, String timee) {
        this.orderid = orderid;
        this.getuser = getuser;
        this.poster = poster;
        this.state = state;
        this.getpost = getpost;
        this.timee = timee;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getGetuser() {
        return getuser;
    }

    public void setGetuser(String getuser) {
        this.getuser = getuser;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGetpost() {
        return getpost;
    }

    public void setGetpost(String getpost) {
        this.getpost = getpost;
    }

    public String getTimee() {
        return timee;
    }

    public void setTimee(String timee) {
        this.timee = timee;
    }
}


