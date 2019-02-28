package com.example.jcd160230_pxl172630_project;

public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthDate;
    private String dateAdded;

    public Contact(String fName, String lName, String phone, String bDate, String aDate) {
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phone;
        this.birthDate = bDate;
        this.dateAdded = aDate;
    }
    public void setFirstName(String fName) {
        this.firstName = fName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lName) {
        this.lastName = lName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setBirthDate(String bDate) {
        this.birthDate = bDate;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setDateAdded(String aDate) {
        this.dateAdded = aDate;
    }
    public String getDateAdded() {
        return dateAdded;
    }
}
