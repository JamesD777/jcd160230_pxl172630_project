/******************************************************************************
 * This is an application written for 4301.002, to display a contact list in an
 * android app that is modifiable by the user. It has a list that opens up a
 * specific contact's info when you click their name. This contact information
 * can be modified by the user and is saved when the save button is clicked.
 *
 * Written by James Dunlap(jcd160230) and Perry Lee (pxl172630) at The University
 * of Texas at Dallas starting March 4, 2019, for an Android development course.
 ******************************************************************************/

package com.example.jcd160230_pxl172630_project;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Contact implements Parcelable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthDate;
    private String dateAdded;
    private boolean deleteFlag;

    public Contact() {
        // Empty constructor
    }
    // Basic constructor
    public Contact(String fName, String lName, String phone, String bDate, String aDate) {
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phone;
        this.birthDate = bDate;
        this.dateAdded = aDate;
    }

    // Getters and setters
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

    // Parcelable implementation
    public Contact(Parcel parcel) {
        this.firstName = parcel.readString();
        this.lastName = parcel.readString();
        this.phoneNumber = parcel.readString();
        this.birthDate = parcel.readString();
        this.dateAdded = parcel.readString();
    }
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(birthDate);
        dest.writeString(dateAdded);
    }
    public void readFromParcel(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        birthDate = in.readString();
        dateAdded = in.readString();
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
