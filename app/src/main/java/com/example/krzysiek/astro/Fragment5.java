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
import android.widget.ImageView;
import android.widget.TextView;


import com.example.krzysiek.astro.data.Channel;
import com.example.krzysiek.astro.data.Forecast;
import com.example.krzysiek.astro.data.Item;
import com.example.krzysiek.astro.service.WeatherServiceCallback;
import com.example.krzysiek.astro.service.YahooWeatherService;

public class Fragment5 extends Fragment implements WeatherServiceCallback {

    private TextView[] textViewsDate = new TextView[8];
    private TextView[] textViewsDay  = new TextView[8];
    private TextView[] textViewsHigh = new TextView[8];
    private TextView[] textViewsLow = new TextView[8];
    private TextView[] textViewsCondition = new TextView[8];
    private ImageView[] imageViewsCode  = new ImageView[8];

    private ProgressDialog dialog;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private YahooWeatherService service;
    private  View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_fragment5,container,false);

        fillView();

        preferences = getActivity().getSharedPreferences("com.example.krzysiek.astro", Context.MODE_PRIVATE);
        editor = preferences.edit();

        service = new YahooWeatherService(this);
        service.refreshWeather("Lodz, PL");
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();

        return view;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();
        Item item = channel.getItem();
        for(int i = 0; i <6; i ++) {
            Forecast forecast = item.getForecast(i+1);

            editor.putInt("imageViewStatus5" + i, getResources().getIdentifier("drawable/f" + forecast.getCode(), null, getActivity().getPackageName()));
            editor.putString("textViewsDate5" + i, forecast.getDate());
            editor.putString("textViewsDay5" + i, forecast.getDay());
            editor.putString("textViewsHigh5" + i, forecast.getHigh());
            editor.putString("textViewsLow5" + i, forecast.getLow());
            editor.putString("textViewsText5" + i, forecast.getText());

            editor.commit();

        }

        refreshWeather();
    }

    @Override
    public void serviceFailure(Exception e) {
            refreshWeather();
            dialog.hide();
    }

    private void fillView(){
        for (int i = 0; i < 6; i++) {
            textViewsDate[i] = view.findViewById(getResources().getIdentifier("textViewDate" + (i+1), "id", getContext().getPackageName()));
            textViewsDay[i] = view.findViewById(getResources().getIdentifier("textViewDay" + (i+1), "id", getContext().getPackageName()));
            textViewsHigh[i] = view.findViewById(getResources().getIdentifier("textViewHigh" + (i+1), "id", getContext().getPackageName()));
            textViewsLow[i] = view.findViewById(getResources().getIdentifier("textViewLow" + (i+1), "id", getContext().getPackageName()));
            textViewsCondition[i] = view.findViewById(getResources().getIdentifier("textViewText" + (i+1), "id", getContext().getPackageName()));
            imageViewsCode[i] = view.findViewById(getResources().getIdentifier("imageViewCode" + (i+1), "id", getContext().getPackageName()));
        }
    }

    @Override
    public void refreshWeather() {
        for(int i = 0; i < 6; i ++) {

            textViewsDate[i].setText(preferences.getString("textViewsDate5"+ i, ""));
            textViewsDay[i].setText(preferences.getString("textViewsDay5"+ i, ""));
            textViewsHigh[i].setText(preferences.getString("textViewsHigh5"+ i, "")+"*C");
            textViewsLow[i].setText(preferences.getString("textViewsLow5"+ i, "")+"*C");
            textViewsCondition[i].setText(preferences.getString("textViewsText5"+ i, ""));

            int resourceID = preferences.getInt("imageViewStatus5"+ i, 0);
            Drawable weatherIconDrawable = getResources().getDrawable(resourceID);
            imageViewsCode[i].setImageDrawable(weatherIconDrawable);
        }
    }
}