package com.example.qrcodebysensors.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcodebysensors.Adapters.ContactsAdapter;
import com.example.qrcodebysensors.Models.ContactModel;
import com.example.qrcodebysensors.R;

import java.util.ArrayList;
import java.util.List;

public class Contacts_Fragment extends Fragment {
    View v;

    public Contacts_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.contacts_fragment, container, false);

        ////////////////////////////////////////////////////
        RecyclerView recyclerView = v.findViewById(R.id.recycleView_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        ContactsAdapter adapter = new ContactsAdapter(getContext(), getContacts());
        recyclerView.setAdapter(adapter);
        ////////////////////////////////////////////////////

        return v;
    }

    private List<ContactModel> getContacts() {

        List<ContactModel> list = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            list.add(new ContactModel(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )), cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
        }

        return list;
    }
}
