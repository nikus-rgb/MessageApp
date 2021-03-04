package com.example.qrcodebysensors.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrcodebysensors.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pl.droidsonroids.gif.GifImageView;

public class Accelerometer_Activity extends AppCompatActivity implements SensorEventListener {

    private Sensor mySensor;
    private SensorManager SM;

    private Button start, stop;
    private FileWriter writer;

    GifImageView hourglass, movePhone;
    TextView info, info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_);

        Toolbar toolbar = findViewById(R.id.accelerometerToolbarOrg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Accelerometer");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        start = findViewById(R.id.buttonStart);
        stop = findViewById(R.id.buttonStop);
        stop.setEnabled(false);

        movePhone = findViewById(R.id.movePhone);
        hourglass = findViewById(R.id.hourglass);
        info = findViewById(R.id.info);
        info2 = findViewById(R.id.info2);

        hourglass.setVisibility(View.GONE);
        info2.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStartClick(View v) {
        /**************************S T A R T**************************************/
        start.setEnabled(false);
        stop.setEnabled(true);

        movePhone.setVisibility(View.GONE);
        info.setVisibility(View.GONE);
        hourglass.setVisibility(View.VISIBLE);
        info2.setVisibility(View.VISIBLE);

        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(this, "Key generation has started.", Toast.LENGTH_LONG).show();
    }

    public void onStopClick(View view) {
        stop.setEnabled(false);
        start.setEnabled(true);

        hourglass.setVisibility(View.GONE);
        info2.setVisibility(View.GONE);
        movePhone.setVisibility(View.VISIBLE);
        info.setVisibility(View.VISIBLE);

        SM.unregisterListener(this);
        Toast.makeText(this, "Key generation has stopped.", Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Data has been written to " + getExternalFilesDir(null), Toast.LENGTH_LONG).show();
    }

    /***********************W R I T E     I N     F I L E**************************************/
    protected void onResume() {
        super.onResume();
        File file = new File(getExternalFilesDir(null), "Notepad");

        if (!file.exists()) {
            file.mkdirs();
        }

        File childFile = new File(file, "generatedKey.txt");

        try {
            writer = new FileWriter(childFile);

            // toast-ul acesta este scris cu scopul de a verifica unde a fost scris fisierul
            //Toast.makeText(this, "Data has been written to " + getExternalFilesDir(null) + "/" + fileName, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onPause() {
        super.onPause();

        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**********************O N    S E N S O R    C H A N G E D**************************************/
    public void onSensorChanged(SensorEvent event) {

        float value1 = event.values[0];
        float value2 = event.values[1];
        float value3 = event.values[2];

        String numX = Float.toString(value1);
        String numY = Float.toString(value2);
        String numZ = Float.toString(value3);

        char lastChX = numX.charAt(numX.length() - 1);
        char lastChY = numY.charAt(numY.length() - 1);
        char lastChZ = numZ.charAt(numZ.length() - 1);

        int lastDecX = Character.getNumericValue(lastChX);
        int lastDecY = Character.getNumericValue(lastChY);
        int lastDecZ = Character.getNumericValue(lastChZ);

        try {
//            writer.write(Integer.toBinaryString(lastDecX)+" "+Integer.toBinaryString(lastDecY)+" "+Integer.toBinaryString(lastDecZ)+"\n");
            writer.write(lastDecX+""+lastDecY+""+lastDecZ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}