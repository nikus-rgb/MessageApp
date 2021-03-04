package com.example.qrcodebysensors.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.qrcodebysensors.Adapters.ViewPagerAdapter;
import com.example.qrcodebysensors.Fragments.AppUsers_Fragment;
import com.example.qrcodebysensors.Fragments.Contacts_Fragment;
import com.example.qrcodebysensors.R;
import com.google.android.material.tabs.TabLayout;


public class Contacts_Activity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private String name, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = findViewById(R.id.contactsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add fragment
        adapter.addFragment(new Contacts_Fragment(), "Contacts");
        adapter.addFragment(new AppUsers_Fragment(), "App users");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString("name");
            number = extras.getString("number");

            final AppUsers_Fragment frg = new AppUsers_Fragment();

            Bundle b1 = new Bundle();
            b1.putString("name", name);
            b1.putString("number", number);
            frg.setArguments(b1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}