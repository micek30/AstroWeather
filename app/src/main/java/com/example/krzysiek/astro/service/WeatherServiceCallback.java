package com.example.krzysiek.astro.service;

import com.example.krzysiek.astro.data.Channel;

/**
 * Created by Krzysiek on 14.06.2018.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception ex);
 }
