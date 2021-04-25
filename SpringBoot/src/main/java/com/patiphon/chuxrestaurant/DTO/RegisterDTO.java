package com.patiphon.chuxrestaurant.DTO;

public class RegisterDTO {
    private String fn;
    private String ln;
    private String em;
    private String phone;
    private String passwd;
    private Boolean getRmd;

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Boolean getGetRmd() {
        return getRmd;
    }

    public void setGetRmd(Boolean getRmd) {
        this.getRmd = getRmd;
    }
}
