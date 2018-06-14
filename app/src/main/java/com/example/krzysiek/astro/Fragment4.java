package com.example.krzysiek.astro;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.krzysiek.astro.data.Atmosphere;
import com.example.krzysiek.astro.data.Channel;
import com.example.krzysiek.astro.data.Item;
import com.example.krzysiek.astro.databinding.FragmentFragment4Binding;
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
    //private FragmentFragment3Binding fragment3LayoutBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        fragment4LayoutBinding = DataBindingUtil.inflate(
//                inflater, R.layout.fragment_fragment3, container, false);
//
//        View view = fragment4LayoutBinding.getRoot();
//
//        fragment4LayoutBinding.setMoon(MainActivity.moon);
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_fragment3,container,false);
        weatherIconImageView = (ImageView)view.findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)view.findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)view.findViewById(R.id.conditionTextView);
        locationTextView = (TextView)view.findViewById(R.id.locationTextView);
        airPressureTextView = (TextView)view.findViewById(R.id.airPressureTextView);
        latitudeTextView = (TextView)view.findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView)view.findViewById(R.id.longitudeTextView);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("Lodz");



        return view;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        Atmosphere atmosphere = channel.getAtmosphere();
        int resourceId = getResources().getIdentifier("drawable/f"+ item.getCondition().getCode(),null,getActivity().getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId,null);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        temperatureTextView.setText(item.getCondition().getTemperature()+"\u00B0"+channel.getUnit().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());
        airPressureTextView.setText(atmosphere.getPressure().toString()+" \u33D4");
        latitudeTextView.setText(item.getLat().toString());
        longitudeTextView.setText(item.getLongi().toString());

    }

    @Override
    public void serviceFailure(Exception ex) {
        //Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();

    }
}
