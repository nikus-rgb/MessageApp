package com.example.qrcodebysensors.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrcodebysensors.R;
import com.google.zxing.WriterException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DisplayQRCode_Activity extends AppCompatActivity {

    private String TAG = "GenerateQrCode";
    private ImageView qrimg;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder; // adaugat in prealabil prin librarie ( Android->build.grandle(Module: app) )
    private String inputvalue;
    private Button generateQR;
    private EditText myNam, myNr;
    private String namNr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_q_r_code);

        Toolbar toolbar = findViewById(R.id.qrcodeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your QR Code");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        qrimg = findViewById(R.id.qrcode);
        try {
            displayQRCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*************SCRIEREA IN FISIER SI GENERAREA CODULUI QR*******************/
    public void displayQRCode() throws IOException {
        File file = new File(getExternalFilesDir(null), "Notepad");
        final File childFile = new File(file, "generatedKey.txt");
        final FileWriter fw = new FileWriter(childFile, true);

        myNam = findViewById(R.id.myName);
        myNr = findViewById(R.id.myNumber);
        generateQR = findViewById(R.id.btnGenerateQR);

        if (!file.exists()) {
            TextView noFile = findViewById(R.id.tvNoFile);
            noFile.setText("You need to generate your key.");
            myNam.setVisibility(View.GONE);
            myNr.setVisibility(View.GONE);
            generateQR.setEnabled(false);
            Toast.makeText(this, "You need to generate the key", Toast.LENGTH_SHORT).show();
        } else {
            if (file.length() == 0) {
                TextView noFile = findViewById(R.id.tvNoFile);
                noFile.setText("You need to generate your key.");
                myNam.setVisibility(View.GONE);
                myNr.setVisibility(View.GONE);
                generateQR.setEnabled(false);
                Toast.makeText(this, "You need to generate the key", Toast.LENGTH_SHORT).show();
            } else if (!childFile.exists()) {
                TextView noFile = findViewById(R.id.tvNoFile);
                noFile.setText("You need to generate your key.");
                myNam.setVisibility(View.GONE);
                myNr.setVisibility(View.GONE);
                generateQR.setEnabled(false);
                Toast.makeText(this, "You need to generate the key", Toast.LENGTH_SHORT).show();
            }else if (childFile.length() == 0) {
            TextView noFile = findViewById(R.id.tvNoFile);
            noFile.setText("You need to generate your key.");
            myNam.setVisibility(View.GONE);
            myNr.setVisibility(View.GONE);
            generateQR.setEnabled(false);
            Toast.makeText(this, "You need to generate the key", Toast.LENGTH_SHORT).show();
        } else {
            myNam.setVisibility(View.VISIBLE);
            myNr.setVisibility(View.VISIBLE);
            generateQR.setEnabled(true);

            fw.write(".");

            generateQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    namNr = myNam.getText().toString() + ":" + myNr.getText().toString();

                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(childFile));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] key = line.split("\\.");
                            line = key[0] + ".";
                            sb.append("").append(line);
                        }

                        fw.write(namNr);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /**Aici adaug numele si numarul utilizatorului in codul QR*/
                    inputvalue = (sb.toString().trim() + namNr);

                    myNam.setVisibility(View.GONE);
                    myNr.setVisibility(View.GONE);
                    qrCode(inputvalue);
                    generateQR.setEnabled(false);
                }
            });
        }
    }
}

    public void qrCode(String input) {
        if (input != null) {

            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);

            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;

            smallerDimension = (smallerDimension * 3) / 4;

            qrgEncoder = new QRGEncoder(input, null, QRGContents.Type.TEXT, smallerDimension);

            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrimg.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        } else {
            qrimg.setImageBitmap(null);
        }
    }

    /**************************************************************************/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}