package com.example.demophonenoverification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneInformationActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE_PHONE_STATE = 1;

    private static final String LOG_TAG = "AndroidExample";

    private TextView editTextPhoneNumbers;
    private TextView txtSimOprater;

    private Button btnlocation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phoneinfo);

        this.editTextPhoneNumbers = this.findViewById(R.id.editText_infos);
        this.txtSimOprater = this.findViewById(R.id.txtSimOprater);
        this.btnlocation = findViewById(R.id.btnlocation);
        askPermissionAndGetPhoneNumbers();


        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), GetLocation.class);
                startActivity(i);
            }
        });
    }





    private void askPermissionAndGetPhoneNumbers() {

        // With Android Level >= 23, you have to ask the user
        // for permission to get Phone Number.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have READ_PHONE_STATE permission
            int readPhoneStatePermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE);

            if ( readPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSION_REQUEST_CODE_PHONE_STATE
                );
                return;
            }
        }
        this.getPhoneNumbers();
    }

    // Need to ask user for permission: android.permission.READ_PHONE_STATE
    @SuppressLint("MissingPermission")
    private void getPhoneNumbers() {
        try {
            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

            @SuppressLint("HardwareIds") String phoneNumber1 = manager.getLine1Number();
            String SimOperator = manager.getSimOperatorName();
          /*  Toast.makeText(this,"Your SimOperator : " + SimOperator,
                    Toast.LENGTH_LONG).show();
*/
            this.editTextPhoneNumbers.setText(phoneNumber1);
            this.txtSimOprater.setText(SimOperator);

            //
         /*   Log.i( LOG_TAG,"Your Phone Number: " + phoneNumber1);
            Toast.makeText(this,"Your Phone Number: " + phoneNumber1,
                    Toast.LENGTH_LONG).show();
*/
            // Other Informations:
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // API Level 26.
                String imei = manager.getImei();
                int phoneCount = manager.getPhoneCount();

                Log.i(LOG_TAG,"Phone Count: " + phoneCount);
                Log.i(LOG_TAG,"EMEI: " + imei);
            }
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) { // API Level 28.
                SignalStrength signalStrength = manager.getSignalStrength();
                int level = signalStrength.getLevel();

                Log.i(LOG_TAG,"Signal Strength Level: " + level);
            }

        } catch (Exception ex) {
            Log.e( LOG_TAG,"Error: ", ex);
            Toast.makeText(this,"Error: " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE_PHONE_STATE: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (SEND_SMS).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                    this.getPhoneNumbers();

                }
                // Cancelled or denied.
                else {
                    Log.i( LOG_TAG,"Permission denied!");
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }


    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_PHONE_STATE) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }




}

