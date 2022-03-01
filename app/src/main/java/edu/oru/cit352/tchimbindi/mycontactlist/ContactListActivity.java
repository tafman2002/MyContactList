// Name: Tafadzwa Chimbindi
// Course: CIT 352: Mobile App Development
// Assignment: MyContactList App
// Professor: Dr. Osborne
// Purpose: This file defines the logic of the ContactListActivity where the list of Contacts will be
// displayed
// Date: 16 February 2022

package edu.oru.cit352.tchimbindi.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {
    private ArrayList<Contact> contacts;
    private RecyclerView contactList;
    private ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        // Calls the methods to initialize the List, Settings, and Map
        initListButton();
        initMapButton();
        initSettingsButton();
        initAddContactButton();
        initDeleteSwitch();
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
        ContactDataSource ds = new ContactDataSource(this);

        try {
            ds.open();
            contacts = ds.getContacts(sortBy, sortOrder);
            ds.close();
            contactList = findViewById(R.id.rvContacts);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((this));
            contactList.setLayoutManager(layoutManager);
            contactAdapter = new ContactAdapter(contacts, this);
            contactAdapter.setOnItemClickListener(onItemClickListener);
            contactList.setAdapter(contactAdapter);
        } catch (SQLException throwables) {
            Toast.makeText(this, "Error receiving contacts", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
        ContactDataSource ds = new ContactDataSource(this);
        try {
            ds.open();
            contacts = ds.getContacts(sortBy, sortOrder);
            ds.close();
            if(contacts.size() > 0) {
                ds.close();
                if (contacts.size() > 0) {
                    contactList = findViewById(R.id.rvContacts);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((this));
                    contactList.setLayoutManager(layoutManager);
                    ContactAdapter contactAdapter = new ContactAdapter(contacts, this);
                    contactAdapter.setOnItemClickListener(onItemClickListener);
                    contactList.setAdapter(contactAdapter);
                } else {
                    Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error receiving contacts", Toast.LENGTH_LONG).show();
        }
    }

    private void initAddContactButton() {
        Button newContact = findViewById(R.id.buttonAddContact);
        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean status = buttonView.isChecked();
                contactAdapter.setItem(status);
                contactAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initListButton() {
        // Initializes the ContactList button and disables the Button so that the user can't interact with it
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setEnabled(false);
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int contactId = contacts.get(position).getContactID();
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            intent.putExtra("contactId", contactId);
            startActivity(intent);
        }
    };

    private void initMapButton() {
        // Initializes the Map button variable
        ImageButton ibMap = findViewById(R.id.imageButtonMap);
        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactMapActivity page which will start once the user clicks on the button
                Intent intent = new Intent(ContactListActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        // Initializes the Setting button variable
        ImageButton ibSetting = findViewById(R.id.imageButtonSettings);
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactSettingsActivity page which will start once the user clicks on the button
                Intent intent = new Intent(ContactListActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}