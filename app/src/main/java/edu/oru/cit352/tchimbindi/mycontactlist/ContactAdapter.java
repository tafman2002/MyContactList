package edu.oru.cit352.tchimbindi.mycontactlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter {
    private ArrayList<Contact> contactData;                                      //1
    private View.OnClickListener mOnItemClickListener;                           //2
    private boolean isDeleting;
    private Context parentContext;

    public ContactAdapter(ArrayList<Contact> arrayList, Context context) {
        contactData = arrayList;
        parentContext = context;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewContact;                                        //3
        public TextView textPhone;                                              //
        public Button deleteButton;                                             //

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textContactName);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);            //4
            deleteButton = itemView.findViewById(R.id.buttonDeleteContact);     //
            itemView.setTag(this);                                              //5
            itemView.setOnClickListener(mOnItemClickListener);                  //
        }

        public TextView getContactTextView() {
            return textViewContact;
        }

        public TextView getPhoneTextView() {                                    //6
            return textPhone;                                                   //
        }                                                                       //
        public Button getDeleteButton() {                                       //
            return deleteButton;                                                //
        }                                                                       //
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {    //7
        mOnItemClickListener = itemClickListener;                                   //
    }                                                                               //

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;
        cvh.getContactTextView().setText(contactData.get(position).getContactName());   //8
        cvh.getPhoneTextView().setText(contactData.get(position).getPhoneNumber());     //
        if (isDeleting) {
            cvh.getDeleteButton().setVisibility(View.VISIBLE);
            cvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);
                }
            });
        } else {
            cvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }

    private void deleteItem(int position) {
        ContactDataSource db = new ContactDataSource(parentContext);
        try {
            db.open();
            db.deleteContact(contactData.get(position).getContactID());
            db.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showDelete(final int position, final View convertView, final Context context, final Contact contact) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteContact);
        if (b.getVisibility() == View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    hideDelete(position, convertView, context);
                    contactData.remove(contact);
                    deleteOption(contact.getContactID(), context);
                }
            });
        }
        else {
            hideDelete(position, convertView, context);
        }
    }

    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteContact);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);
    }

    private void deleteOption(int contactToDelete, Context context) {
        ContactDataSource db = new ContactDataSource(context);
        try {
            db.open();
            db.deleteContact(contactToDelete);
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(parentContext, "Delete Contact Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }

    public void setItem(boolean status){
        isDeleting = status;
    }


    @Override
    public int getItemCount() {
        return contactData.size();
    }
}