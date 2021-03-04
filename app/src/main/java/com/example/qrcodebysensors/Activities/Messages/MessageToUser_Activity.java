package com.example.qrcodebysensors.Activities.Messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import com.example.qrcodebysensors.R;

import org.xxtea.XXTEA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MessageToUser_Activity extends AppCompatActivity {
    private Button userBtnSend;
    private EditText userEditTextMessage;
    private IntentFilter intentFilterC;

    private BufferedReader reader;
    private String actualKey;
    private String userKey;
    private String copyNumber;

    private TextView myKey, usrK;

    private BroadcastReceiver intentUserReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView output = (TextView) findViewById(R.id.messageView);

            /** ExtragereaDecriptarea Mesajului*/
            final String msg = intent.getExtras().getString("message");

            String[] m = msg.split("\\ ");
            String message = m[1];

            File directory = new File(getExternalFilesDir(null), "Users");
            File[] fList = directory.listFiles();

            for (File file : fList) {
                String out = file.getName();
                String userNumber = out.substring(0, out.indexOf("."));

                if (userNumber.equals(copyNumber)) {

                    String line;
                    String[] exKey;
                    try {
                        reader = new BufferedReader(new FileReader(file));

                        while ((line = reader.readLine()) != null) {
                            exKey = line.split("\\.");
                            userKey = exKey[0];

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //usrK.setText(userKey);
            String decrypt_msg = XXTEA.decryptBase64StringToString(message, userKey);
            output.setText(m[0] + "" + decrypt_msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_for_user_);

        Toolbar toolbar = findViewById(R.id.messageForUserToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** Good approach*/
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        final String number = extras.getString("number");
        /****************/

        copyNumber = number;

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setSubtitle(number);


        /*******Primire mesaj de la user*********/
        intentFilterC = new IntentFilter();
        intentFilterC.addAction("SMS_RECEIVED_ACTION");
        /*******************************************/

        userBtnSend = (Button) findViewById(R.id.userBtnSend);
        userEditTextMessage = (EditText) findViewById(R.id.userEditTextMessage);

        /**Apelarea de extragere a propriei chei pentru criptare*/
        extractMyKey();
        /*******************************************/

        //myKey = findViewById(R.id.myKey);
        //usrK = findViewById(R.id.userKey);
        //myKey.setText(actualKey);


        userBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = userEditTextMessage.getText().toString();
                sendMsgToUser(number, msg);
                userEditTextMessage.setText("");
            }
        });
    }

    protected void sendMsgToUser(String theNumber, String myMsg) {
            String SENT = "Message Sent";
            String DELIVERED = "Message Delivered";

            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

            SmsManager sms = SmsManager.getDefault();

            /** Metoda de criptare a mesajului si trimiterea mesajului criptat. */
            String encrypt_msg = XXTEA.encryptToBase64String(myMsg, actualKey);
            sms.sendTextMessage(theNumber, null, encrypt_msg, sentPI, deliveredPI);
            /************************************/

            Toast.makeText(this, "Message sent.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Functia de extragere a propriei chei pentru criptare.
     */
    public void extractMyKey() {
        File file = new File(getExternalFilesDir(null), "Notepad");

        if (!file.exists()) {
            Toast.makeText(getApplicationContext(), "You need to generate your key.", Toast.LENGTH_SHORT).show();
        }

        File childFile = new File(file, "generatedKey.txt");

        if (!childFile.exists()) {
            Toast.makeText(getApplicationContext(), "You need to generate your key.", Toast.LENGTH_LONG).show();
        } else {
            if (childFile.length() == 0) {
                Toast.makeText(getApplicationContext(), "You need to generate your key.", Toast.LENGTH_LONG).show();
            } else {
                String line;
                String[] exKey;
                try {
                    reader = new BufferedReader(new FileReader(childFile));
                    while ((line = reader.readLine()) != null) {
                        exKey = line.split("\\.");
                        actualKey = exKey[0];
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(intentUserReceiver, intentFilterC);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentUserReceiver);
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