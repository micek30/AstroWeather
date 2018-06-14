package com.example.krzysiek.astro.data;

import org.json.JSONObject;

/**
 * Created by Krzysiek on 15.06.2018.
 */

public class Wind implements JSONPopulator {
    private Double speed;
    private String direction;

    public Double getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public void populate(JSONObject data) {

        speed = data.optDouble("speed");
        direction = data.optString("direction");
    }
}