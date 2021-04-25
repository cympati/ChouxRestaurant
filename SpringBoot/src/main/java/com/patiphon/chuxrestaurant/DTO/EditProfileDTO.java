package com.patiphon.chuxrestaurant.DTO;

public class EditProfileDTO {
    private String color;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private boolean getReminders;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isGetReminders() {
        return getReminders;
    }

    public void setGetReminders(boolean getReminders) {
        this.getReminders = getReminders;
    }
}
