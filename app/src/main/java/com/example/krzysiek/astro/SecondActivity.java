package com.example.krzysiek.astro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    private EditText editTextLa;
    private EditText editTextLo;
    private EditText editTextRe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_layout);


        editTextLa = (EditText) findViewById(R.id.editTextLatitude);
        editTextLo = (EditText) findViewById(R.id.editTextLongitude);
        editTextRe = (EditText) findViewById(R.id.editTextRefreshTime);

        editTextLa.setText("" + String.valueOf(MainActivity.latitude));
        editTextLo.setText("" + String.valueOf(MainActivity.longitude));
        editTextRe.setText("" + MainActivity.refreshTime/1000);

    }

    public void onClickSave(View view) {

        MainActivity.latitude = Double.parseDouble(editTextLa.getText().toString());
        MainActivity.longitude = Double.parseDouble(editTextLo.getText().toString());
        if(Integer.valueOf(editTextRe.getText().toString()) > 0) {
            MainActivity.refreshTime = Integer.parseInt(editTextRe.getText().toString()) * 1000;
        }
        else
            MainActivity.refreshTime = 1000;

        MainActivity.dateTime.refreshAllTime();
        MainActivity.sun.refresh();
        MainActivity.moon.refresh();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
