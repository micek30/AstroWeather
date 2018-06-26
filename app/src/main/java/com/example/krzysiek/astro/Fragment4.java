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
import com.example.krzysiek.astro.data.Wind;
import com.example.krzysiek.astro.service.WeatherServiceCallback;
import com.example.krzysiek.astro.service.YahooWeatherService;


public class Fragment4 extends Fragment implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private TextView airPressureTextView;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private YahooWeatherService service;
    private ProgressDialog dialog;

    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    TextView windTextView;
    TextView windDirTextView;
    TextView HumTextView;
    TextView VisTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_fragment4,container,false);
        windTextView = (TextView) view.findViewById(R.id.WindtextView);
        windDirTextView = (TextView) view.findViewById(R.id.WindDirtextView);
        HumTextView = (TextView) view.findViewById(R.id.HumtextView);
        VisTextView = (TextView) view.findViewById(R.id.VistextView);

        preferences = getActivity().getSharedPreferences("config.xml", Context.MODE_PRIVATE);
        editor = preferences.edit();

        String location = preferences.getString("selectedLocation", "Lodz");
        String tempScale = preferences.getString("selectedTempForm", "c");

        service = new YahooWeatherService(this);
        service.refreshWeather(location, tempScale);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
//        dialog.show();




        return view;
    }

    @Override
    public void serviceSuccess(Channel channel) {
       dialog.hide();

        Atmosphere atmosphere = channel.getAtmosphere();
        Wind wind = channel.getWind();

        editor.putString("windTextView", wind.getSpeed().toString());
        editor.putString("windDirTextView", wind.getDirection());
        editor.putString("HumtextView", atmosphere.getHumidity());
        editor.putString("VistextView", atmosphere.getVisibility());
        editor.commit();

        refreshWeather();

    }

    @Override
    public void serviceFailure(Exception ex) {
        //Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        refreshWeather();
        dialog.hide();

    }
    @Override
    public void refreshWeather() {

        windTextView.setText(preferences.getString("windTextView", ""));
        windDirTextView.setText(preferences.getString("windDirTextView", ""));
        HumTextView.setText(preferences.getString("HumtextView", ""));
        VisTextView.setText(preferences.getString("VistextView", ""));

    }



}
