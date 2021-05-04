package com.patiphon.chuxrestaurant.DTO;

public class ConfirmPasswordDTO {
    private String confirmPasswd;

    public String getConfirmPasswd() {
        return confirmPasswd;
    }

    public void setConfirmPasswd(String confirmPasswd) {
        this.confirmPasswd = confirmPasswd;
    }

    @Override
    public String toString() {
        return "ConfirmPasswordDTO{" +
                "confirmPasswd='" + confirmPasswd + '\'' +
                '}';
    }
}
