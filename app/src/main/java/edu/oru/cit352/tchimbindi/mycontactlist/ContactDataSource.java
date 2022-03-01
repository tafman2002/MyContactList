// Name: Tafadzwa Chimbindi
// Course: CIT 352: Mobile App Development
// Assignment: MyContactList App
// Professor: Dr. Osborne
// Purpose: This file defines the logic of the ContactDatasource, managing access to the database mycontacts.db
// Date: 16 February 2022

package edu.oru.cit352.tchimbindi.mycontactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class ContactDataSource {

    // Defining attibutes of the class
    private SQLiteDatabase database;
    private final ContactDBHelper dbHelper;

    public ContactDataSource(Context context) {
        dbHelper = new ContactDBHelper(context);
    }

    public void open() throws SQLException {
        // Opens the mycontacts.db
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        // Closes the connection to the database
        dbHelper.close();
    }

    public boolean insertContact(Contact c) {
        // This method will create a new Contact in the database by using the passed in contact's attribute values
        //  which will be placed as a key-value pair in the ContentValues object and attempt to insert to the database.
        // If it succeeds, the didSucceed will change to true and return. Otherwise, the method will return a false value
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("contactname", c.getContactName());
            initialValues.put("streetaddress", c.getStreetAddress());
            initialValues.put("city", c.getCity());
            initialValues.put("state", c.getState());
            initialValues.put("zipcode", c.getZipCode());
            initialValues.put("phonenumber", c.getPhoneNumber());
            initialValues.put("cellnumber", c.getCellNumber());
            initialValues.put("email", c.getEmail());
            initialValues.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));

            // An insert will be attempted and then there will be a check to see if the row was inserted.
            // A value of 0 means that the row was not inserted successfully, so the didSucceed will remain false.
            // Otherwise, a value greater than 0 means the contact was inserted and therefore, didSucceed changes to true
            didSucceed = database.insert("contact", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateContact(Contact c) {
        // This method will update an existing Contact by using the passed in contact's attribute values
        // will be placed as a key-value pair (column name and value) in the ContentValues object and attempt to update it.
        // If it succeeds, the didSucceed will change to true and return. Otherwise, the method will return a false value
        boolean didSucceed = false;
        try {
            Long rowId = (long) c.getContactID();
            ContentValues updateValues = new ContentValues();
            updateValues.put("contactname", c.getContactName());
            updateValues.put("streetaddress", c.getStreetAddress());
            updateValues.put("city", c.getCity());
            updateValues.put("state", c.getState());
            updateValues.put("zipcode", c.getZipCode());
            updateValues.put("phonenumber", c.getPhoneNumber());
            updateValues.put("cellnumber", c.getCellNumber());
            updateValues.put("email", c.getEmail());
            updateValues.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));

            didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean deleteContact(int contactId) {
        // This method opens the database and executes a query to delete the specific
        // contact using the passed in contactId.
        boolean didDelete = false;
        try {
            // The query to delete the contact record from the table will be executed which will return an integer value.
            // A value greater than 0 indicates that the record was deleted and therefore didDelete changes to true. Otherwise,
            // there is no contact to delete therefore the expression would evaluate to false and didDelete woule be set to false
            didDelete = database.delete("contact", "_id=" + contactId, null) > 0;
        } catch (Exception e) {
            // Do Nothing -return value already set to false
        }
        return didDelete;
    }

    public ArrayList<String> getContactName() {
        ArrayList<String> contactNames = new ArrayList<>();
        try {
            String query = "Select contactname from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                contactNames.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            //Error
        }
        return contactNames;
    }

    public ArrayList<Contact> getContacts(String sortField, String sortOrder) {
        // This method opens the database and executes a query to retrieve all of the
        // contacts based on the user's set sortField and sortOrder preferences.
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        try {
            String query = "SELECT * FROM contact ORDER BY " + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);
            Contact newContact;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // While the cursor moves through the results set, a new contact will be created which will
                // set each attribute of the contact object with the appropriate values from the column index before adding that
                // contact object to the arraylist. The cursor then moves on to the next row of the result set
                newContact = new Contact();
                newContact.setContactID(cursor.getInt(0));
                newContact.setContactName(cursor.getString(1));
                newContact.setStreetAddress(cursor.getString(2));
                newContact.setCity(cursor.getString(3));
                newContact.setState(cursor.getString(4));
                newContact.setZipCode(cursor.getString(5));
                newContact.setPhoneNumber(cursor.getString(6));
                newContact.setCellNumber(cursor.getString(7));
                newContact.setEmail(cursor.getString(8));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(9)));
                newContact.setBirthday(calendar);
                contacts.add(newContact);
                cursor.moveToNext();
            }
            cursor.close();         // Closes the cursor
        } catch (Exception e) {
            // A new contact list will be created if there is an issue with executing the query
            contacts = new ArrayList<Contact>();
        }
        // contact list is returned
        return contacts;
    }


    public Contact getSpecificContact(int contactId) {
        // This method opens the database and executes a query to retrieve a specific
        // contact using the contactId provided to the method
        Contact contact = new Contact();
        String query = "SELECT * FROM contact WHERE _id =" + contactId;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            // If the cursor is not empty, it means that the specific contact record was found.
            // As a result, the attributes of the newly created contact object above is are set using the
            // appropriate values from the column index before the cursor closes
            contact.setContactID(cursor.getInt(0));
            contact.setContactName(cursor.getString(1));
            contact.setStreetAddress(cursor.getString(2));
            contact.setCity(cursor.getString(3));
            contact.setState(cursor.getString(4));
            contact.setZipCode(cursor.getString(5));
            contact.setPhoneNumber(cursor.getString(6));
            contact.setCellNumber(cursor.getString(7));
            contact.setEmail(cursor.getString(8));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(9)));
            contact.setBirthday(calendar);
            cursor.close();
        }
        // contact is returned
        return contact;
    }


    public int getLastContactId() {
        // This method will return the last contact ID in the Contact Table. If the table is empty,
        // the default lastId of -1 returned
        int lastId = -1;
        try {
            // First, the maximum value from the contact id is returned from the query
            String query = "Select MAX(_id) from contact";
            Cursor cursor = database.rawQuery(query, null);

            // A result set is returned from the executed query and the id will be set to
            // the lastId
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            // Error
        }
        return lastId;
    }
}