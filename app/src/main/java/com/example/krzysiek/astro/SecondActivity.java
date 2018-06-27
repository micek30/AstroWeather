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
    private Spinner comboCity;
    private Spinner spinnerCF;
    private List<String> cityList = new ArrayList<>();
    private List<String> tempCF = new ArrayList<>();
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
        editTextCity = (EditText) findViewById(R.id.editTextCity);
        comboCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerCF = (Spinner) findViewById(R.id.spinnerCF);

        editTextLa.setText("" + String.valueOf(MainActivity.latitude));
        editTextLo.setText("" + String.valueOf(MainActivity.longitude));
        editTextRe.setText("" + MainActivity.refreshTime/1000);


        cityList.add("Lodz");
        cityList.add("Berlin");
        cityList.add("Moscow");

        tempCF.add("c");
        tempCF.add("f");


        int prefsInt = preferences.getInt("countLocation", 0);
        if(prefsInt != 0){
            count = prefsInt;
            for(int i = 0; i < count; i++){
                cityList.add(preferences.getString("Location" + (i+1), ""));
            }
        }

        int prefsInt2 = preferences.getInt("selectedLocationID", 0);
        int prefTempId = preferences.getInt("selectedTempID", 0);


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        comboCity.setAdapter(adapter);

        ArrayAdapter<String> adapterCF =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, tempCF);
        adapterCF.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerCF.setAdapter(adapterCF);


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

        spinnerCF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                editor.putString("selectedTempForm", selected);
                editor.putInt("selectedTempID", position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        comboCity.setSelection(prefsInt2);
        spinnerCF.setSelection(prefTempId);


    }

    public void onClickSave(View view) {
        if(!editTextCity.getText().toString().equals("")&&!editTextCity.getText().toString().equals("City...")) {
            count++;
            editor.putInt("countLocation", count);
            editor.putString("Location" + count, editTextCity.getText().toString());
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
