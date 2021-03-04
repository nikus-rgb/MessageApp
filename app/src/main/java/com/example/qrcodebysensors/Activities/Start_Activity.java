package com.example.qrcodebysensors.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.qrcodebysensors.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Start_Activity extends AppCompatActivity {

    private FloatingActionButton listFab, searchFab, addFab;
    private TextView textSearch, textAdd;

    private boolean isOpen;

    private Animation openFab, closeFab, rotateFront, rotateBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        permissions();

        // Initializarea barii de unelte(Toolbar)
        Toolbar toolbar = findViewById(R.id.startToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        listFab = findViewById(R.id.list_fab);
        searchFab = findViewById(R.id.search_fab);
        addFab = findViewById(R.id.add_contact_fab);
        textSearch = findViewById(R.id.text_search_fab);
        textAdd = findViewById(R.id.text_add_fab);

        openFab = AnimationUtils.loadAnimation(Start_Activity.this, R.anim.fab_open);
        closeFab = AnimationUtils.loadAnimation(Start_Activity.this, R.anim.fab_close);

        rotateFront = AnimationUtils.loadAnimation(Start_Activity.this, R.anim.rotate_forward);
        rotateBack = AnimationUtils.loadAnimation(Start_Activity.this, R.anim.rotate_backward);

        isOpen = false;

        // Setarea default a butoanelor si textview-urilor pe invisible
        searchFab.setVisibility(View.INVISIBLE);
        addFab.setVisibility(View.INVISIBLE);
        textSearch.setVisibility(View.INVISIBLE);
        textAdd.setVisibility(View.INVISIBLE);

        listFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    listFab.startAnimation(rotateBack);
                    searchFab.startAnimation(closeFab);
                    addFab.startAnimation(closeFab);
                    textSearch.startAnimation(closeFab);
                    textAdd.startAnimation(closeFab);

                    textSearch.setVisibility(View.GONE);
                    textAdd.setVisibility(View.GONE);
                    searchFab.hide();
                    addFab.hide();
                    isOpen = false;
                } else {
                    listFab.startAnimation(rotateFront);
                    searchFab.startAnimation(openFab);
                    addFab.startAnimation(openFab);
                    textSearch.startAnimation(openFab);
                    textAdd.startAnimation(openFab);

                    textSearch.setVisibility(View.VISIBLE);
                    textAdd.setVisibility(View.VISIBLE);
                    searchFab.show();
                    addFab.show();
                    isOpen = true;
                }
            }
        });
    }

    public void permissions() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
        }
    }

    public void onClickSearch(View v) {
        Intent myAccel = new Intent(Start_Activity.this, Contacts_Activity.class);
        startActivity(myAccel);
    }

    public void onClickAdd(View v) {
        Intent intent = new Intent(Start_Activity.this, AddContacts_Activity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent myQRCode = new Intent(this, DisplayQRCode_Activity.class);
                startActivity(myQRCode);

                Toast.makeText(this, "Your QR Code activity", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Intent scanQRCode = new Intent(this, ScanQRCode_Activity.class);
                startActivity(scanQRCode);

                Toast.makeText(this, "Scan a friend's QR Code", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Intent myAccelIntent = new Intent(this, Accelerometer_Activity.class);
                startActivity(myAccelIntent);

                Toast.makeText(this, "Accelerometer activity", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
