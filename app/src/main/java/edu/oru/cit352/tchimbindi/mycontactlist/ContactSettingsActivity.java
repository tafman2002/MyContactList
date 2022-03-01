// Name: Tafadzwa Chimbindi
// Course: CIT 352: Mobile App Development
// Assignment: MyContactList App
// Professor: Dr. Osborne
// Purpose: This program allows users to enter their first and last name which can be
// displayed on the screen.
// Date: 16 February 2022
package edu.oru.cit352.tchimbindi.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ContactSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_settings);
        initListButton();
        initMapButton();
        initSettingsButton();
        initSettings();
        initSortOrderClick();
        initSortByClick();
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // Declaring radioButtons for the radioGroup sortBy
                RadioButton rbName = findViewById(R.id.radioName);
                RadioButton rbCity = findViewById(R.id.radioCity);
                if (rbName.isChecked()) {
                    // Applies the new sort by Name setting value to the SharedPreferences
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "contactname").apply();
                }
                else if (rbCity.isChecked()) {
                    // Applies the new sort by City setting value to the SharedPreferences
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "city").apply();
                }
                else {
                    // Applies the new sort by Birthday setting value to the SharedPreferences
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "birthday").apply();
                }
            }
        });
    }

    private void initSortOrderClick() {
        // Declaring radioButtons for the radioGroup sortOrder
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = findViewById(R.id.radioAscending);
                if (rbAscending.isChecked()) {
                    // Applies the new sort order Ascending setting value to the SharedPreferences
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                }
                else {
                    // Applies the new sort order Descending setting value to the SharedPreferences
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
                }
            }
        });
    }

    private void initSettings() {
        // Retrieves the sortBy and sortOrder setting value from the SharedPreferences. If there is no value, default values (contactname, ASC) will be used instead
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
        
        // Declaring radioButton variables 
        RadioButton rbName = findViewById(R.id.radioName);
        RadioButton rbCity = findViewById(R.id.radioCity);
        RadioButton rbBirthday = findViewById(R.id.radioBirthday);
        RadioButton rbAscending = findViewById(R.id.radioAscending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);
        
        if(sortBy.equalsIgnoreCase("contactname")) {
            // The contactList will be sorted by Name 
            rbName.setChecked(true);
        } else if (sortBy.equalsIgnoreCase("city")) {
            // THe contactList will be sorted by City 
            rbCity.setChecked(true);
        } else {
            // THe contactList will be sorted by Birthday
            rbBirthday.setChecked(true);
        }
        if(sortOrder.equalsIgnoreCase("ASC")) {
            // The contactList will be sorted in ascending order
            rbAscending.setChecked(true);
        } else {
            // The contactList will be sorted in descending order 
            rbDescending.setChecked(true);
        }

    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactListActivity page which will start once the user clicks on the button
                Intent intent = new Intent(ContactSettingsActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMapButton() {
        ImageButton ibMap = findViewById(R.id.imageButtonMap);
        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactMapActivity page which will start once the user clicks on the button
                Intent intent = new Intent(ContactSettingsActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        // Disables the Setting Button
        ImageButton ibSetting = findViewById(R.id.imageButtonSettings);
        ibSetting.setEnabled(false);
    }
}