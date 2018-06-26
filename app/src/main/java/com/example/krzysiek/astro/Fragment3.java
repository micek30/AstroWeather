package com.example.krzysiek.astro;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.krzysiek.astro.data.Atmosphere;
import com.example.krzysiek.astro.data.Channel;
import com.example.krzysiek.astro.data.Item;
import com.example.krzysiek.astro.service.WeatherServiceCallback;
import com.example.krzysiek.astro.service.YahooWeatherService;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private TextView airPressureTextView;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private YahooWeatherService service;
    private Spinner comboCity;
    private List<String> cityList = new ArrayList<>();
    private ProgressDialog dialog;

    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private Button buttonSave;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_fragment3,container,false);
        weatherIconImageView = (ImageView)view.findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)view.findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)view.findViewById(R.id.conditionTextView);
        locationTextView = (TextView)view.findViewById(R.id.locationTextView);
        airPressureTextView = (TextView)view.findViewById(R.id.airPressureTextView);
        latitudeTextView = (TextView)view.findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView)view.findViewById(R.id.longitudeTextView);


        preferences = getActivity().getSharedPreferences("config.xml", Context.MODE_PRIVATE);
        editor = preferences.edit();

        String location = preferences.getString("selectedLocation", "Lodz");
        String tempScale = preferences.getString("selectedTemp", "c");

        service = new YahooWeatherService(this);
        service.refreshWeather(location, tempScale);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();




        return view;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        Atmosphere atmosphere = channel.getAtmosphere();

        editor.putInt("imageViewStatus30", getResources().getIdentifier("drawable/f" + item.getCondition().getCode(), null, getActivity().getPackageName()));
        editor.putString("textViewTemp30", item.getCondition().getTemperature()+" \u00B0"+channel.getUnits().getTemperature());
        editor.putString("textViewLocation30", service.getLocation());
        editor.putString("textViewDesc30", item.getCondition().getDescription());
        editor.putString("textViewPreasure30", atmosphere.getPressure().toString()+" \u33D4");
        editor.putString("textViewLat30", item.getLat().toString());
        editor.putString("textViewLong30", item.getLongi().toString());

        editor.commit();


        int resourceID =  preferences.getInt("imageViewStatus30", 0);
        Drawable weatherIconDrawable = getResources().getDrawable(resourceID);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(preferences.getString("textViewTemp30", ""));
        locationTextView.setText(preferences.getString("textViewLocation30", ""));
        conditionTextView.setText(preferences.getString("textViewDesc30", ""));
        airPressureTextView.setText(preferences.getString("textViewPreasure30", ""));
        latitudeTextView.setText(preferences.getString("textViewLat30", ""));
        longitudeTextView.setText(preferences.getString("textViewLong30", ""));

        refreshWeather();

    }

    @Override
    public void serviceFailure(Exception ex) {
        refreshWeather();
        dialog.hide();
    }
    @Override
    public void refreshWeather() {

        int resourceID =  preferences.getInt("imageViewStatus30", 0);
        Drawable weatherIconDrawable = getResources().getDrawable(resourceID);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(preferences.getString("textViewTemp30", ""));
        locationTextView.setText(preferences.getString("textViewLocation30", ""));
        conditionTextView.setText(preferences.getString("textViewDesc30", ""));
        airPressureTextView.setText(preferences.getString("textViewPreasure30", ""));
        latitudeTextView.setText(preferences.getString("textViewLat30", ""));
        longitudeTextView.setText(preferences.getString("textViewLong30", ""));
    }



}
