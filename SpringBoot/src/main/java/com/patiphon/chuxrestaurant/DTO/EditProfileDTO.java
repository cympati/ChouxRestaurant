package com.patiphon.chuxrestaurant.DTO;

public class EditProfileDTO {
    private String color;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int getReminders;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
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

    public int isGetReminders() {
        return getReminders;
    }

    public void setGetReminders(int getReminders) {
        this.getReminders = getReminders;
    }

    @Override
    public String toString() {
        return "EditProfileDTO{" +
                "color='" + color + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", getReminders=" + getReminders +
                '}';
    }
}
