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

public class DatePickerDialog extends DialogFragment {                          //1

    // Save Date method that is implemented in this fragment
    public interface SaveDateListener { void didFinishDatePickerDialog(Calendar selectedTime); }

    public DatePickerDialog() {                                                  //3
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {                                           //4
        final View view = inflater.inflate(R.layout.select_date, container);
        getDialog().setTitle("Select Date");
        Calendar selectedTime = Calendar.getInstance();
        final CalendarView cv = view.findViewById(R.id.birthdayPicker);
        // Sets a listener on the calenderview which will update the date
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedTime.set(year, month, dayOfMonth);
            }
        });

        // Saves the date selected by the user
        Button saveButton = (Button) view.findViewById(R.id.buttonSelect);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                selectedTime.setTimeInMillis(dp.getDate());
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

    private void saveItem(Calendar selectedTime) {                               //6
        SaveDateListener activity = (SaveDateListener) getActivity();
        activity.didFinishDatePickerDialog(selectedTime);
        getDialog().dismiss();

    }


}