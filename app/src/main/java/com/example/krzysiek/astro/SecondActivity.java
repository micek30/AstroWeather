package com.example.krzysiek.astro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private EditText editTextLa;
    private EditText editTextLo;
    private EditText editTextRe;
    private EditText editTextCity;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private Button buttonSave;
    private EditText editText;
    private Spinner comboCity;
    private List<String> cityList = new ArrayList<>();
    private  int count=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_layout);

        preferences = this.getSharedPreferences("config.xml", Context.MODE_PRIVATE);
        editor = preferences.edit();

        editTextLa = (EditText) findViewById(R.id.editTextLatitude);
        editTextLo = (EditText) findViewById(R.id.editTextLongitude);
        editTextRe = (EditText) findViewById(R.id.editTextRefreshTime);
        editTextCity = (EditText) findViewById(R.id.editText);

        editTextLa.setText("" + String.valueOf(MainActivity.latitude));
        editTextLo.setText("" + String.valueOf(MainActivity.longitude));
        editTextRe.setText("" + MainActivity.refreshTime/1000);

        editText = (EditText)this.findViewById(R.id.editText);
        buttonSave = (Button) this.findViewById(R.id.buttonSave);

        cityList.add("Lodz");
        cityList.add("Warszawa");


        int prefsInt = preferences.getInt("howMuch6", 0);
        if(prefsInt != 0){
            count = prefsInt;
            for(int i = 0; i < count; i++){
                cityList.add(preferences.getString("wordPlace" + (i+1), ""));
            }
        }

        int prefsInt2 = preferences.getInt("selectedLocationID", 0);


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        comboCity.setAdapter(adapter);


        comboCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                editor.putString("selectedLocation", selected);
                editor.putInt("selectedLocationID", position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        comboCity.setSelection(prefsInt2);


    }

    public void onClickSave(View view) {
        if(!editTextCity.getText().toString().equals("")) {
            count++;
            editor.putInt("howMuch6", count);
            editor.putString("wordPlace" + count, editTextCity.getText().toString());
            editor.commit();
        }

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
