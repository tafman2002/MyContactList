// Name: Tafadzwa Chimbindi
// Course: CIT 352: Mobile App Development
// Assignment: MyContactList App
// Professor: Dr. Osborne
// Purpose: This file defines the logic that specifies the database to use, creates the table(s), and
// manages updates to database structure (versions)
// Date: 16 February 2022

package edu.oru.cit352.tchimbindi.mycontactlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mycontacts.db";    // Database Name
    private static final int DATABASE_VERSION = 1;   // Database Version Number

    // Database creation sql statement
    private static final String CREATE_TABLE_CONTACT =
            "create table contact (_id integer primary key autoincrement, "
                    + "contactname text not null, streetaddress text, "
                    + "city text, state text, zipcode text, "
                    + "phonenumber text, cellnumber text, "
                    + "email text, birthday text);";

    // Constructor which will call the parent's constructor
    public ContactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Will create a table called Contact when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);
    }

    // Will Drop the existing Contacts table, deleting all the data before the mycontacts database
    // is rebuilt
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ContactDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db);
    }

}