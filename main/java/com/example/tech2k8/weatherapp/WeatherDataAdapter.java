package com.example.tech2k8.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.WeatherViewHolder> {

    private ArrayList<WeatherData> weatherData;
    private Context context;

    public WeatherDataAdapter(ArrayList<WeatherData> weatherData,Context context) {
        this.weatherData = weatherData;
        this.context=context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.recyclerview_row,viewGroup,false);
        WeatherViewHolder holder =new WeatherViewHolder(rowView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        WeatherData data =weatherData.get(i);
        weatherViewHolder.lat.setText(data.getLatitude());
        weatherViewHolder.lon.setText(data.getLongitude());
        weatherViewHolder.temp.setText(data.getTemperature());

    }

    @Override
    public int getItemCount() {
        return weatherData.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder
    {

        TextView lat,lon,temp;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            lat=itemView.findViewById(R.id.lat);
            lon=itemView.findViewById(R.id.lon);
            temp=itemView.findViewById(R.id.temp);
        }
    }
}
