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

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class File_IO {
    static String contactsFile = "contacts.txt";
    private static File dir;
    static PrintWriter pw = null;

    /****************************************************************************
     * Read contacts from file and set to ArrayList
     * Author: Perry Lee
     * ****************************************************************************/
    public static ArrayList readContactsFile(Context context, int whichData) {
        ArrayList<Contact> contactsArray = new ArrayList<>();

        try {
            FileInputStream file = context.openFileInput(contactsFile);
            InputStreamReader inputReader;
            if(whichData == 0) {
                inputReader = new InputStreamReader((context.getAssets().open("contacts.txt")));
            }
            else {
                inputReader = new InputStreamReader(file);
            }
            BufferedReader reader = new BufferedReader(inputReader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split("\\t");
                contactsArray.add(new Contact(row[0], row[1], row[2], row[3], row[4]));
                System.out.println(row[0]);
            }
            //reader.close();
            inputReader.close();
            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return contactsArray;
    }

    /****************************************************************************
     * Write newly constructed contacts ArrayList and write to file.
     * Author: James Dunlap, Perry Lee
     * ****************************************************************************/
    public static void writeContactsFile(Context context, ArrayList<Contact> contactsArrayList) {
        File findFile = new File(context.getFilesDir(), contactsFile);
        //open files
        try {
            dir = new File(context.getFilesDir(), contactsFile);
            pw = new PrintWriter(dir);

            FileInputStream file = context.openFileInput(contactsFile);
            InputStreamReader inputReader = new InputStreamReader(file);
            BufferedReader reader = new BufferedReader(inputReader);
            FileOutputStream fileOutput = context.openFileOutput(contactsFile, Context.MODE_PRIVATE);
            //loop through the data, printing it
            for(Contact c: contactsArrayList) {
                String row = c.getFirstName() + "\t" + c.getLastName() + "\t" + c.getPhoneNumber() + "\t" + c.getBirthDate() + "\t" + c.getDateAdded();
                System.out.println("Write: " + row);
                pw.println(row);
            }
            pw.close();
            fileOutput.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
