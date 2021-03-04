package com.example.qrcodebysensors.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcodebysensors.Adapters.UserAdapter;
import com.example.qrcodebysensors.Models.UserModel;
import com.example.qrcodebysensors.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppUsers_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private BufferedReader reader;
    View v;

    public AppUsers_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.app_user_fragment, container, false);

        List<UserModel> userModel = new ArrayList<>();

        File directory = new File(getActivity().getExternalFilesDir(null), "Users");
        File[] fList = directory.listFiles();

        if (!directory.exists()) {
            Toast.makeText(getContext(), "You need to add at least 1 user.", Toast.LENGTH_LONG).show();
        } else {
            if (directory.length() == 0) {
                Toast.makeText(getContext(), "You need to add at least 1 user.", Toast.LENGTH_LONG).show();
            } else {
                for (File file : fList) {
                    String out = file.getName();
                    String number = out.substring(0, out.indexOf("."));
                    String line;

                    String userName = null;
                    try {
                        reader = new BufferedReader(new FileReader(file));

                        while ((line = reader.readLine()) != null) {
                            String[] key = line.split("\\.");
                            String[] user = key[1].split("\\:");
                            userName = user[0];
                        }
                        userModel.add(new UserModel(userName, number));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        mRecyclerView = v.findViewById(R.id.recycleView_users);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new UserAdapter(userModel, getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
}
