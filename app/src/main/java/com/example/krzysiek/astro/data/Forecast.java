package com.example.krzysiek.astro.data;

import org.json.JSONObject;

public class Forecast implements  JSONPopulator{
    private String code;
    private String date;
    private String day;
    private String high;
    private String low;
    private String text;

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getText() {
        return text;
    }


    @Override
    public void populate(JSONObject data) {

        code = data.optString("code");
        date = data.optString("date");
        day = data.optString("day");
        high = data.optString("high");
        low = data.optString("low");
        text = data.optString("text");
    }
}