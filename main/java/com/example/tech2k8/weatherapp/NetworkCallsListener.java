package com.example.tech2k8.weatherapp;

import java.util.ArrayList;

public interface NetworkCallsListener {

    void onSuccess(ArrayList<WeatherData> data);

    void onFailure();
}
