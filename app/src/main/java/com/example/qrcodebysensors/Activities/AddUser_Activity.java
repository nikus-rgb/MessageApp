package com.example.qrcodebysensors.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qrcodebysensors.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddUser_Activity extends AppCompatActivity {

    private EditText cName, cNumber;
    private Button add;
    private String name, number, key;
    FileWriter writer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_);

        Toolbar toolbar = findViewById(R.id.addUserToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add User");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cName = findViewById(R.id.contactName);
        cNumber = findViewById(R.id.contactNumber);
        add = findViewById(R.id.addContact);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString("name");
            number = extras.getString("number");
            key = extras.getString("key");

            cName.setText(name);
            cNumber.setText(number);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File(getExternalFilesDir(null), "Users");

                if (!file.exists()) {
                    file.mkdir();
                    File childFile = new File(file, number);
                    try {
                        writer = new FileWriter(childFile);

                        if (!childFile.exists()) {
                            childFile.mkdir();
                            writer.write(key);
                            writer.close();
                        }
                        writer.write(key);
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    File childFile = new File(file, number+".txt");
                    try {
                        writer = new FileWriter(childFile);

                        if (!childFile.exists()) {
                            childFile.mkdir();
                            writer.write(key);
                            writer.close();
                        }
                        writer.write(key);
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText(getApplicationContext(),"User has been added.",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Start_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}