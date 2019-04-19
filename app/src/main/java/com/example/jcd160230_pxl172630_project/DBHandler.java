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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
/****************************************************************************
 * Handler class for the database to make working with it easy
 * Author: Perry Lee, James Dunlap
 * ****************************************************************************/
public class DBHandler extends SQLiteOpenHelper {

    private static final String CONTACTS_TABLE = "contacts";
    private static final String KEY_ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String BIRTH_DATE = "birthDate";
    private static final String DATE_ADDED = "dateAdded";
    private static final String POSTAL_1 = "postal1";
    private static final String POSTAL_2 = "postal2";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String ZIPCODE = "zipCode";

    /****************************************************************************
     * Constructor which sets up the handler
     * Author: Perry Lee
     * ****************************************************************************/
    public DBHandler(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    /****************************************************************************
     * Oncreate function for the dbhandler, sets up the contacts table
     * Author: Perry Lee
     * ****************************************************************************/
    public void onCreate(SQLiteDatabase database) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACTS_TABLE + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + FIRST_NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + PHONE_NUMBER + " TEXT, "
                + BIRTH_DATE + " TEXT, "
                + DATE_ADDED + " TEXT, "
                + POSTAL_1 + " TEXT, "
                + POSTAL_2 + " TEXT, "
                + CITY + " TEXT, "
                + STATE + " TEXT, "
                + ZIPCODE + " TEXT);";
        database.execSQL(CREATE_CONTACTS_TABLE);
    }

    /****************************************************************************
     * Upgrades the database with a new one and recreates the contacts table
     * Author: Perry Lee
     * ****************************************************************************/
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);

        onCreate(database);
    }

    /******************************************************************************
     * Add a new contact to the database with the info given by the user
     * Author: Perry Lee, James Dunlap
     * ****************************************************************************/
    void addContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, contact.getFirstName());
        values.put(LAST_NAME, contact.getLastName());
        values.put(PHONE_NUMBER, contact.getPhoneNumber());
        values.put(BIRTH_DATE, contact.getBirthDate());
        values.put(DATE_ADDED, contact.getDateAdded());
        values.put(POSTAL_1, contact.getPostal());
        values.put(POSTAL_2, contact.getPostal2());
        values.put(CITY, contact.getCity());
        values.put(STATE, contact.getState());
        values.put(ZIPCODE, contact.getZipCode());
        //check if the contact already exists, stops the db from having copies of the same person
        Cursor cursor = database.rawQuery("SELECT * FROM " + CONTACTS_TABLE + " WHERE " + PHONE_NUMBER + "= '" + contact.getPhoneNumber() + "'", null);
        if(!cursor.moveToFirst()) //if the query is null, this contact is new and can be added, if copy, dont add
            database.insert(CONTACTS_TABLE, null, values);
        database.close();
    }

    /****************************************************************************
     * Get the contact of a specific person from the database using their ID. Called
     * when you want to modify their data
     * Author: Perry Lee
     * ****************************************************************************/
    Contact getContact(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + CONTACTS_TABLE + " WHERE " + KEY_ID + "=" + id, null);
        cursor.moveToFirst();
        Contact contact = new Contact();
        contact.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        contact.setFirstName(cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
        contact.setLastName(cursor.getString(cursor.getColumnIndex(LAST_NAME)));
        contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(PHONE_NUMBER)));
        contact.setBirthDate(cursor.getString(cursor.getColumnIndex(BIRTH_DATE)));
        contact.setDateAdded(cursor.getString(cursor.getColumnIndex(DATE_ADDED)));
        contact.setPostal(cursor.getString(cursor.getColumnIndex(POSTAL_1)));
        contact.setPostal2(cursor.getString(cursor.getColumnIndex(POSTAL_2)));
        contact.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
        contact.setState(cursor.getString(cursor.getColumnIndex(STATE)));
        contact.setZipCode(cursor.getString(cursor.getColumnIndex(ZIPCODE)));

        return contact;
    }
    /****************************************************************************
     * Get all of the contacts from the database, called when populating list initially
     * Author: Perry Lee
     * ****************************************************************************/
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT * FROM " + CONTACTS_TABLE;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) { //if not null return from the query
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setPhoneNumber((cursor.getString(3)));
                contact.setBirthDate(cursor.getString(4));
                contact.setDateAdded(cursor.getString(5));
                contact.setPostal(cursor.getString(6));
                contact.setPostal2(cursor.getString(7));
                contact.setCity(cursor.getString(8));
                contact.setState(cursor.getString(9));
                contact.setZipCode(cursor.getString(10));
                contactList.add(contact);
                System.out.println(contact.getFirstName());
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    /****************************************************************************
     * Update a contact in the database with new data
     * Author: Perry Lee
     * ****************************************************************************/
    public int updateContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, contact.getFirstName());
        values.put(LAST_NAME, contact.getLastName());
        values.put(PHONE_NUMBER, contact.getPhoneNumber());
        values.put(BIRTH_DATE, contact.getBirthDate());
        values.put(POSTAL_1, contact.getPostal());
        values.put(POSTAL_2, contact.getPostal2());
        values.put(CITY, contact.getCity());
        values.put(STATE, contact.getState());
        values.put(ZIPCODE, contact.getZipCode());

        return database.update(CONTACTS_TABLE, values, KEY_ID + " = ?", new String[] {String.valueOf(contact.getID())});
    }

    /****************************************************************************
     * Delete a contact from the database when the delete button is clicked
     * Author: Perry Lee
     * ****************************************************************************/
    public void deleteContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(CONTACTS_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(contact.getID())});
        database.close();
    }
}
