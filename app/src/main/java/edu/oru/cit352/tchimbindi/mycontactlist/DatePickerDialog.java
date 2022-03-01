// Name: Tafadzwa Chimbindi
// Course: CIT 352: Mobile App Development
// Assignment: MyContactList App
// Professor: Dr. Osborne
// Purpose: This file defines the logic of the DatePicker Dialog which will be shown to users
// Date: 16 February 2022
package edu.oru.cit352.tchimbindi.mycontactlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class DatePickerDialog extends DialogFragment {

    // Save Date method that is implemented in this fragment
    public interface SaveDateListener { void didFinishDatePickerDialog(Calendar selectedTime); }

    public DatePickerDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.select_date, container);
        getDialog().setTitle("Select Date");
        // Declares the Calendar instance variable
        Calendar selectedTime = Calendar.getInstance();
        final CalendarView cv = view.findViewById(R.id.birthdayPicker);
        // Sets a listener on the calenderview which will update the date selected by user to the selectedTime variable
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedTime.set(year, month, dayOfMonth);
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.buttonSelect);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Calls the saveItem method which will save the date selected by the user
                saveItem(selectedTime);

            }
        });

        // Cancels the Calender view or dismisses the dialog from the screen
        Button cancelButton = (Button) view.findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    private void saveItem(Calendar selectedTime) {
        // Saves the date selected by the user and returns it to the MainActivity to update the birthday textView by
        // calling the didFinishDatePickerDialog method from the MainActivity
        SaveDateListener activity = (SaveDateListener) getActivity();
        activity.didFinishDatePickerDialog(selectedTime);
        getDialog().dismiss();

    }


}