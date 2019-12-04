package com.example.zhy_gdapp.beans;

public class Comments {

    private String id,user,poser,comm,timee;

    public Comments(String id, String user, String poser, String comm, String timee) {
        this.id = id;
        this.user = user;
        this.poser = poser;
        this.comm = comm;
        this.timee = timee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPoser() {
        return poser;
    }

    public void setPoser(String poser) {
        this.poser = poser;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getTimee() {
        return timee;
    }

    public void setTimee(String timee) {
        this.timee = timee;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", poser='" + poser + '\'' +
                ", comm='" + comm + '\'' +
                ", timee='" + timee + '\'' +
                '}';
    }
}
