package com.patiphon.chuxrestaurant.DTO;

public class AddReserveDTO {
    private long date_time;
    private int size;
    private String req;

    public long getDate_time() {
        return date_time;
    }

    public void setDate_time(long date_time) {
        this.date_time = date_time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }
}