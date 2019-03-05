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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private List<Contact> contactList;
    private LayoutInflater inflater;

    // Constructor
    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.contactList = contactList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contactList.size();
    }
    public Contact getItem(int position) {
        return contactList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    static class ContactHolder {
        TextView firstName;
        TextView lastName;
    }

    // Get View and inflate
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contactlist_layout, null);
            holder = new ContactHolder();
            holder.firstName = (TextView) convertView.findViewById(R.id.fName);
            holder.lastName = (TextView) convertView.findViewById(R.id.lName);

            convertView.setTag(holder);
        }
        else {
            holder = (ContactHolder)convertView.getTag();
        }
        holder.firstName.setText(contactList.get(position).getFirstName());
        holder.lastName.setText(contactList.get(position).getLastName());

        return convertView;
    }
}
