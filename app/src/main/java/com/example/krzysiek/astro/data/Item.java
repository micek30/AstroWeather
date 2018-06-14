package com.example.krzysiek.astro.data;

import org.json.JSONObject;

/**
 * Created by Krzysiek on 14.06.2018.
 */

public class Item implements JSONPopulator {
    private Condition condition;
    private Double lat;
    private Double longi;

    public Double getLat() {
        return lat;
    }

    public Double getLongi() {
        return longi;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));

        lat = data.optDouble("lat");
        longi = data.optDouble("long");
    }
}
