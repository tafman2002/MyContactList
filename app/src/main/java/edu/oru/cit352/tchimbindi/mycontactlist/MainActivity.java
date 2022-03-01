// Name: Tafadzwa Chimbindi
// Course: CIT 352: Mobile App Development
// Assignment: MyContactList App
// Professor: Dr. Osborne
// Purpose: This file defines the logic and layout of the MainActivity File which will contain the page for
// users to create a new Contact
// Date: 16 February 2022
package edu.oru.cit352.tchimbindi.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContentInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Button;
import android.text.format.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {
    // Defining new Contact Object
    private Contact currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Calls the methods to initialize all the buttons and fields in the layout file
        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        initChangeDateButton();
        initTextChangedEvents();
        initSaveButton();

        // Creates a new contact
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            initContact(extras.getInt("contactId"));
        } else {
            currentContact = new Contact();
        }


    }

    private void initContact(int id) {
        ContactDataSource ds = new ContactDataSource(MainActivity.this);
        try {
            ds.open();
            currentContact = ds.getSpecificContact(id);
            ds.close();
        }
        catch (Exception e) {
            Toast.makeText(this,"Load Contact Failed", Toast.LENGTH_LONG).show();
        }

        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editAddress = (EditText) findViewById(R.id.editAddress);
        EditText editCity = (EditText) findViewById(R.id.editCity);
        EditText editState = (EditText) findViewById(R.id.editState);
        EditText editZipCode = (EditText) findViewById(R.id.editZipcode);
        EditText editPhone = (EditText) findViewById(R.id.editHome);
        EditText editCell = (EditText) findViewById(R.id.editCell);
        EditText editEmail = (EditText) findViewById(R.id.editEmail);
        TextView birthDay = (TextView) findViewById(R.id.textBirthday);
        editName.setText(currentContact.getContactName());
        editAddress.setText(currentContact.getStreetAddress());
        editCity.setText(currentContact.getCity());
        editState.setText(currentContact.getState());
        editZipCode.setText(currentContact.getZipCode());
        editPhone.setText(currentContact.getPhoneNumber());
        editCell.setText(currentContact.getCellNumber());
        editEmail.setText(currentContact.getEmail());
        birthDay.setText(DateFormat.format("MM/dd/yyyy",currentContact.getBirthday().getTimeInMillis()).toString());
    }


    private void initListButton() {
        // Declares the List button
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactListActivity page which will start once the user clicks on the button
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMapButton() {
        // Declares the Map button
        ImageButton ibMap = findViewById(R.id.imageButtonMap);
        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactMapActivity page which will start once the user clicks on the button
                Intent intent = new Intent(MainActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        // Declares the Setting button
        ImageButton ibSetting = findViewById(R.id.imageButtonSettings);
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes an intent to the ContactSettingsActivity page which will start once the user clicks on the button
                Intent intent = new Intent(MainActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initToggleButton() {
        // Declares the toggle button
        final ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calls the setForEditing method, passing in the changed state (boolean) of the toggle button
                setForEditing(editToggle.isChecked());
            }
        });
    }

    private void setForEditing(boolean enabled) {
        // Declaring and initializing the EditText Fields and Buttons
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editAddress = (EditText) findViewById(R.id.editAddress);
        EditText editCity = (EditText) findViewById(R.id.editCity);
        EditText editState = (EditText) findViewById(R.id.editState);
        EditText editZipCode = (EditText) findViewById(R.id.editZipcode);
        EditText editPhone = (EditText) findViewById(R.id.editHome);
        EditText editCell = (EditText) findViewById(R.id.editCell);
        EditText editEmail = (EditText) findViewById(R.id.editEmail);
        Button buttonChange = (Button) findViewById(R.id.buttonBirthday);
        Button buttonSave = (Button) findViewById(R.id.buttonSave);

        // Sets the state of the EditText fields: if true, the fields will be on for the user to interact with; if false,
        // the fields will be off and the user is not able to make changes
        editName.setEnabled(enabled);
        editAddress.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editState.setEnabled(enabled);
        editZipCode.setEnabled(enabled);
        editPhone.setEnabled(enabled);
        editCell.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        buttonChange.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

        if (enabled) {
            // if enabled is true, the fields will be edible and the focus shall be put on the Name field
            editName.requestFocus();
        } else {
            // The screenview shall scroll back to the top of the page if the enabled is false
            ScrollView s = (ScrollView) findViewById(R.id.scrollViewContact);
            s.fullScroll(ScrollView.FOCUS_UP);
            s.clearFocus();
        }

    }

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        // Declares the birthday text variable
        TextView birthday = findViewById(R.id.textBirthday);
        birthday.setText(DateFormat.format("MM/dd/yyyy", selectedTime));    // Sets the birthday text to the date selected by user in DatePicker Dialog
        currentContact.setBirthday(selectedTime);
    }

    private void initChangeDateButton() {
        // Declares the changeDate button variable
        Button changeDate = findViewById(R.id.buttonBirthday);
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens a DatePicker Dialog for users to select their date of birth
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(fm, "DatePick");
            }
        });
    }

    private void initTextChangedEvents(){
        final EditText etContactName = findViewById(R.id.editName);
        etContactName.addTextChangedListener(new TextWatcher() {
            // This will listen to any changes made to the ContactName field, updating the ContactName attribute
            // of the currentContact
            public void afterTextChanged(Editable s) {
                currentContact.setContactName(etContactName.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });

        final EditText etStreetAddress = findViewById(R.id.editAddress);
        etStreetAddress.addTextChangedListener(new TextWatcher() {
            // This will listen to any changes made to the Address field, updating the Contact Street Address attribute
            // of the currentContact
            public void afterTextChanged(Editable s) {
                currentContact.setStreetAddress(etStreetAddress.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });

        final EditText etCity = findViewById(R.id.editCity);
        etCity.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // This will listen to any changes made to the City field, updating the Contact City attribute
            // of the currentContact
            public void afterTextChanged(Editable editable) {
                currentContact.setCity(etCity.getText().toString());
            }
        });

        final EditText etState = findViewById(R.id.editState);
        etState.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // This will listen to any changes made to the State field, updating the Contact State attribute
            // of the currentContact
            public void afterTextChanged(Editable editable) {
                currentContact.setState(etState.getText().toString());
            }
        });

        final EditText etZip = findViewById(R.id.editZipcode);
        etZip.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // This will listen to any changes made to the Zipcode field, updating the Contact Zipcode attribute
            // of the currentContact
            public void afterTextChanged(Editable editable) {
                currentContact.setZipCode(etZip.getText().toString());
            }
        });

        final EditText etPhone = findViewById(R.id.editHome);
        etPhone.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                currentContact.setPhoneNumber(etPhone.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });

        final EditText etCell = findViewById(R.id.editCell);
        etCell.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            // This will listen to any changes made to the Cell phone field, updating the Contact Cell phone attribute
            // of the currentContact
            public void afterTextChanged(Editable editable) {
                currentContact.setCellNumber(etCell.getText().toString());
            }
        });

        final EditText etEMail = findViewById(R.id.editEmail);
        etEMail.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            // This will listen to any changes made to the Email field, updating the Contact Email attribute
            // of the currentContact
            public void afterTextChanged(Editable editable) {
                currentContact.setEmail(etEMail.getText().toString());
            }
        });

        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        etCell.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }
    private void initSaveButton() {
        // Sets a listener to the save button which, if clicked, will save the contact
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                hideKeyboard();
                ContactDataSource ds = new ContactDataSource(MainActivity.this);
                try {
                    // The DataSource object will open the mycontacts database and check the currentContacts ID to see if
                    // the contact is new (ID is -1 by default) or an existing contact in the database (ID other than -1)
                    ds.open();

                    if (currentContact.getContactID() == -1) {
                        // The currentContact is a new entry to the database and attempt to insert the entry
                        wasSuccessful = ds.insertContact(currentContact);
                        if (wasSuccessful) {
                            // If the insert was successful, wasSuccessful is set to true and the new ID set to the entry by the database is returned from the
                            // getLastContactId method and the currentContact ID will be updated to the new ID.
                            int newID = ds.getLastContactId();
                            currentContact.setContactID(newID);
                        }
                    }
                    else {
                        // The contact is an existing contact, so an update to the entry will be done instead and wasSuccessful is set to true
                        wasSuccessful = ds.updateContact(currentContact);
                    }

                    // Connection to the database is closed
                    ds.close();
                }
                catch (Exception e) {
                    // Insert or Update was unsuccessful so wasSuccessful is set to false
                    wasSuccessful = false;
                    Toast.makeText(MainActivity.this, "Contact was unable to save", Toast.LENGTH_SHORT).show();

                }

                // The Contact Entries will be disabled if the insert or update is successfully
                if (wasSuccessful) {
                    // Pop up a dialog of success
                    Toast.makeText(MainActivity.this, "Contact created successfully", Toast.LENGTH_SHORT).show();
                    ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editName = findViewById(R.id.editName);
        imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
        EditText editAddress = findViewById(R.id.editAddress);
        imm.hideSoftInputFromWindow(editAddress.getWindowToken(), 0);
        EditText et = findViewById(R.id.editCity);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et = findViewById(R.id.editState);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et = findViewById(R.id.editZipcode);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et = findViewById(R.id.editHome);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et = findViewById(R.id.editCell);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et = findViewById(R.id.editEmail);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

}