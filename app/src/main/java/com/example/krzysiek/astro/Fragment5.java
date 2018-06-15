package com.example.krzysiek.astro;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.krzysiek.astro.data.Channel;
import com.example.krzysiek.astro.data.Forecast;
import com.example.krzysiek.astro.data.Item;
import com.example.krzysiek.astro.service.WeatherServiceCallback;
import com.example.krzysiek.astro.service.YahooWeatherService;

public class Fragment5 extends Fragment implements WeatherServiceCallback {

    private TextView[] textViewsDate = new TextView[3];

    private TextView[] textViewsDay  = new TextView[3];

    private TextView[] textViewsHigh = new TextView[3];

    private TextView[] textViewsLow = new TextView[3];

    private TextView[] textViewsText = new TextView[3];

    private ImageView[] imageViewsCode  = new ImageView[3];



    private YahooWeatherService yahooWaetherSevice;
    private  View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_fragment5,container,false);

        fillView();

        yahooWaetherSevice = new YahooWeatherService(this);
        yahooWaetherSevice.refreshWeather("Lodz, PL");


        return view;
    }

    @Override
    public void serviceSuccess(Channel channel) {

        Item item = channel.getItem();
        for(int i = 0; i < 3; i ++) {
            Forecast forecast = item.getForecast(i+1);

            textViewsDate[i].setText(forecast.getDate());
            textViewsDay[i].setText(forecast.getDay());
            textViewsHigh[i].setText(forecast.getHigh());
            textViewsLow[i].setText(forecast.getLow());
            textViewsText[i].setText(forecast.getText());

            int resourceID = getResources().getIdentifier("drawable/f" + item.getForecast(i+1).getCode(), null, getActivity().getPackageName());

            //@SuppressWarnings("deprecation")
            Drawable weatherIconDrawable = getResources().getDrawable(resourceID);

            imageViewsCode[i].setImageDrawable(weatherIconDrawable);
        }





    }

    @Override
    public void serviceFailure(Exception e) {

    }

    private void fillView(){
        for (int i = 0; i < 3; i++) {
            textViewsDate[i] = view.findViewById(getResources().getIdentifier("textViewDate" + (i+1), "id", getContext().getPackageName()));
            textViewsDay[i] = view.findViewById(getResources().getIdentifier("textViewDay" + (i+1), "id", getContext().getPackageName()));
            textViewsHigh[i] = view.findViewById(getResources().getIdentifier("textViewHigh" + (i+1), "id", getContext().getPackageName()));
            textViewsLow[i] = view.findViewById(getResources().getIdentifier("textViewLow" + (i+1), "id", getContext().getPackageName()));
            textViewsText[i] = view.findViewById(getResources().getIdentifier("textViewText" + (i+1), "id", getContext().getPackageName()));
            imageViewsCode[i] = view.findViewById(getResources().getIdentifier("imageViewCode" + (i+1), "id", getContext().getPackageName()));
        }
    }
}