/******************************************************************************
 * This is an application written for 4301.002, to display a contact list in an
 * android app that is modifiable by the user. It has a list that opens up a
 * specific contact's info when you click their name. This contact information
 * can be modified by the user and is saved to a sqlite database when the save
 * button is clicked.
 *
 * Written by James Dunlap(jcd160230) and Perry Lee (pxl172630) at The University
 * of Texas at Dallas starting March 4, 2019, for an Android development course.
 ******************************************************************************/

package com.example.jcd160230_pxl172630_project;

import android.os.Parcel;
import android.os.Parcelable;

/****************************************************************************
 * Sets up the contact parcelable for contacts, no more comments needed
 * Author: Perry Lee, James Dunlap
 * ****************************************************************************/
public class Contact implements Parcelable {
    private int keyID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthDate;
    private String dateAdded;
    private String postal;
    private String postal2;
    private String city;
    private String state;
    private String zipCode;

    public Contact() {
        // Empty constructor
    }
    // FileIO
    public Contact(String fName, String lName, String phone, String bDate, String aDate) {
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phone;
        this.birthDate = bDate;
        this.dateAdded = aDate;
    }
    // Basic constructor
    public Contact(String fName, String lName, String phone, String bDate, String aDate, String postal, String postal2, String city, String state, String zipCode) {
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phone;
        this.birthDate = bDate;
        this.dateAdded = aDate;
        this.postal = postal;
        this.postal2 = postal2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    // Database helper
    public Contact(int id, String fName, String lName, String phone, String bDate, String aDate, String postal, String postal2, String city, String state, String zipCode) {
        this.keyID = id;
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phone;
        this.birthDate = bDate;
        this.dateAdded = aDate;
        this.postal = postal;
        this.postal2 = postal2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    // Getters and setters
    public void setID(int id) {
        this.keyID = id;
    }
    public int getID() {
        return keyID;
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
    public void setPostal(String postal) {
        this.postal = postal;
    }
    public String getPostal() {
        return postal;
    }
    public void setPostal2(String postal2) {
        this.postal2 = postal2;
    }
    public String getPostal2() {
        return postal2;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getZipCode() {
        return zipCode;
    }

    // Parcelable implementation
    public Contact(Parcel parcel) {
        this.keyID = parcel.readInt();
        this.firstName = parcel.readString();
        this.lastName = parcel.readString();
        this.phoneNumber = parcel.readString();
        this.birthDate = parcel.readString();
        this.dateAdded = parcel.readString();
        this.postal = parcel.readString();
        this.postal2 = parcel.readString();
        this.city = parcel.readString();
        this.state = parcel.readString();
        this.zipCode = parcel.readString();
    }
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(keyID);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(birthDate);
        dest.writeString(dateAdded);
        dest.writeString(postal);
        dest.writeString(postal2);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zipCode);
    }
    public void readFromParcel(Parcel in) {
        keyID = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        birthDate = in.readString();
        dateAdded = in.readString();
        postal = in.readString();
        postal2 = in.readString();
        city = in.readString();
        state = in.readString();
        zipCode = in.readString();
    }
    public static Creator<Contact> CREATOR = new Creator<Contact>() {
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
