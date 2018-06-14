package com.example.krzysiek.astro.data;

import org.json.JSONObject;

/**
 * Created by Krzysiek on 14.06.2018.
 */

public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
