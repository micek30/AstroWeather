package com.example.krzysiek.astro.data;

import org.json.JSONObject;

/**
 * Created by Krzysiek on 15.06.2018.
 */

public class Atmosphere implements JSONPopulator{
    private Double pressure;
    private String humidity;
    private String visibility;

    public String getHumidity() {
        return humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public Double getPressure() {
        return pressure;
    }

    @Override
    public void populate(JSONObject data) {

        pressure = data.optDouble("pressure");
        humidity = data.optString("humidity");
        visibility = data.optString("visibility");
    }
}
