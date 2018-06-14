package com.example.krzysiek.astro.data;

import org.json.JSONObject;

/**
 * Created by Krzysiek on 14.06.2018.
 */

public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
