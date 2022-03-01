// Name: Tafadzwa Chimbindi
// Course: CIT 352: Mobile App Development
// Assignment: MyContactList App
// Professor: Dr. Osborne
// Purpose: This file defines the logic of the ContactListActivity where the list of Contacts will be
// displayed
// Date: 16 February 2022
package edu.oru.cit352.tchimbindi.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ContactMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_map);

        // Calls the methods defined below to initialize the widgets in the layout
        initListButton();
        initMapButton();
        initSettingsButton();
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactListActivity page which will start once the user clicks on the button
                Intent intent = new Intent(ContactMapActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMapButton() {
        // Disables the Map button
        ImageButton ibMap = findViewById(R.id.imageButtonMap);
        ibMap.setEnabled(false);
    }

    private void initSettingsButton() {
        ImageButton ibSetting = findViewById(R.id.imageButtonSettings);
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactSettingsActivity page which will start once the user clicks on the button
                Intent intent = new Intent(ContactMapActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}