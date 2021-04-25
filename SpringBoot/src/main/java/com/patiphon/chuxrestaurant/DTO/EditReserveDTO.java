package com.patiphon.chuxrestaurant.DTO;

public class EditReserveDTO {
    private String special_req;
//    private int size;
    private int id_rsv;

    public String getSpecial_req() {
        return special_req;
    }

    public void setSpecial_req(String special_req) {
        this.special_req = special_req;
    }

//    public int getSize() {
//        return size;
//    }
//
//    public void setSize(int size) {
//        this.size = size;
//    }

    public int getId_rsv() {
        return id_rsv;
    }

    public void setId_rsv(int id_rsv) {
        this.id_rsv = id_rsv;
    }

}
