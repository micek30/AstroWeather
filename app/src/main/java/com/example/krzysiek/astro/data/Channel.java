package com.example.krzysiek.astro.data;

import org.json.JSONObject;

/**
 * Created by Krzysiek on 14.06.2018.
 */

public class Channel implements JSONPopulator {
    private Units units;
    private Item item;

    public Units getUnit() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void populate(JSONObject data) {

        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));
    }
}
