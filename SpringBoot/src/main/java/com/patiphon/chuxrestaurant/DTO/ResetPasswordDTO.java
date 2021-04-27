package com.patiphon.chuxrestaurant.DTO;

public class ResetPasswordDTO {
    private int id_reset;
    private String new_passwd;
    private String verify;

    public int getId_reset() {
        return id_reset;
    }

    public void setId_reset(int id_reset) {
        this.id_reset = id_reset;
    }

    public String getNew_passwd() {
        return new_passwd;
    }

    public void setNew_passwd(String new_passwd) {
        this.new_passwd = new_passwd;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }
}
