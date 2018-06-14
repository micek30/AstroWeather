package com.example.krzysiek.astro.data;

import org.json.JSONObject;

/**
 * Created by Krzysiek on 14.06.2018.
 */

public class Channel implements JSONPopulator {
    private Units units;
    private Item item;
    private Wind wind;
    private Atmosphere atmosphere;

    public Units getUnit() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public Wind getWind() {
        return wind;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void populate(JSONObject data) {

        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

        atmosphere = new Atmosphere();
        atmosphere.populate(data.optJSONObject("atmosphere"));

        wind = new Wind();
        wind.populate(data.optJSONObject("wind"));
    }
}
