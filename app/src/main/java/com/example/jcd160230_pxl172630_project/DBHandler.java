package com.example.jcd160230_pxl172630_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    //private static final int DB_VERSION = 14; //13
    //private static final String DB_NAME = "ContactsDatabase";
    private static final String CONTACTS_TABLE = "contacts";
    private static final String KEY_ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String BIRTH_DATE = "birthDate";
    private static final String DATE_ADDED = "dateAdded";

    public DBHandler(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }
    public void onCreate(SQLiteDatabase database) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACTS_TABLE + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + FIRST_NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + PHONE_NUMBER + " TEXT, "
                + BIRTH_DATE + " TEXT, "
                + DATE_ADDED + " TEXT);";
        database.execSQL(CREATE_CONTACTS_TABLE);
    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);

        onCreate(database);
    }
    void addContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, contact.getFirstName());
        values.put(LAST_NAME, contact.getLastName());
        values.put(PHONE_NUMBER, contact.getPhoneNumber());
        values.put(BIRTH_DATE, contact.getBirthDate());
        values.put(DATE_ADDED, contact.getDateAdded());

        database.insert(CONTACTS_TABLE, null, values);
        database.close();
    }
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

        return contact;
    }
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT * FROM " + CONTACTS_TABLE;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setPhoneNumber((cursor.getString(3)));
                contact.setBirthDate(cursor.getString(4));
                contact.setDateAdded(cursor.getString(5));
                contactList.add(contact);
                System.out.println(contact.getFirstName());
            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public int updateContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, contact.getFirstName());
        values.put(LAST_NAME, contact.getLastName());
        values.put(PHONE_NUMBER, contact.getPhoneNumber());
        values.put(BIRTH_DATE, contact.getBirthDate());
        values.put(DATE_ADDED, contact.getDateAdded());

        return database.update(CONTACTS_TABLE, values, KEY_ID + " = ?", new String[] {String.valueOf(contact.getID())});
    }
    public void deleteContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(CONTACTS_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(contact.getID())});
        database.close();
    }
}
