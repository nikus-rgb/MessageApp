package com.example.qrcodebysensors.Activities.Messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.qrcodebysensors.R;

public class MessageToContact_Activity extends AppCompatActivity {

    private Button btnSend;
    private EditText tvMessage;
    private IntentFilter intentFilter;

    private BroadcastReceiver intentReceiverC = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            TextView inTxt = findViewById(R.id.textMsg);
            inTxt.setText(intent.getExtras().getString("message"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_to_contact);

        Toolbar toolbar = findViewById(R.id.messageToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** Good approach*/
        Bundle extras = getIntent().getExtras();
        final String name = extras.getString("name");
        final String number = extras.getString("number");
        /****************/

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setSubtitle(number);

        /*******Primire mesaj de la un contact*********/
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        /**********************************************/

        btnSend = (Button) findViewById(R.id.btnSend);
        tvMessage = (EditText) findViewById(R.id.tvMessage);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myMsg = tvMessage.getText().toString();
                sendMsg(number, myMsg);
                tvMessage.setText("");
            }
        });
    }

    protected void sendMsg(String theNumber, String myMsg){
            String SENT = "Message Sent";
            String DELIVERED = "Message Delivered";

            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(theNumber, null, myMsg, sentPI, deliveredPI);
            Toast.makeText(this,"Message sent.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiverC, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReceiverC);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}