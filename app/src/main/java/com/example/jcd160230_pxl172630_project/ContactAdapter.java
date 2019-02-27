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

    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.contactList = contactList;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return contactList.size();
    }
    public Object getItem(int position) {
        return contactList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    static class ContactHolder {
        TextView firstName;
        TextView lastName;
        TextView phoneNumber;
        TextView birthDate;
        TextView dateAdded;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ContactHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contactlist_layout, null);
            holder = new ContactHolder();
            holder.firstName = (TextView) convertView.findViewById(R.id.fName);
            holder.lastName = (TextView) convertView.findViewById(R.id.lName);
            holder.phoneNumber = (TextView) convertView.findViewById(R.id.phone);
            holder.birthDate = (TextView) convertView.findViewById(R.id.bDate);
            holder.dateAdded = (TextView) convertView.findViewById(R.id.aDate);

            convertView.setTag(holder);
        }
        else {
            holder = (ContactHolder)convertView.getTag();
        }
        holder.firstName.setText(contactList.get(position).getFirstName());
        holder.lastName.setText(contactList.get(position).getLastName());
        holder.phoneNumber.setText(contactList.get(position).getPhoneNumber());
        holder.birthDate.setText(contactList.get(position).getBirthDate());
        holder.dateAdded.setText(contactList.get(position).getDateAdded());

        return convertView;
    }
}
