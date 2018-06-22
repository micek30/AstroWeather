package com.example.krzysiek.astro.data;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Krzysiek on 14.06.2018.
 */

public class Item implements JSONPopulator {
    private JSONArray jsonArray;
    private Condition condition;
    private Double lat;
    private Double longi;
    private Forecast[] forecast;

    public Double getLat() {
        return lat;
    }

    public Double getLongi() {
        return longi;
    }

    public Condition getCondition() {
        return condition;
    }

    public Forecast getForecast(int i) {
        return forecast[i];
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));

        jsonArray = data.optJSONArray("forecast");
        forecast = new Forecast[8];
        for (int i = 0; i < 8; i++) {
            try {
                forecast[i] = new Forecast();
                forecast[i].populate(jsonArray.getJSONObject(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lat = data.optDouble("lat");
        longi = data.optDouble("long");
    }
}
