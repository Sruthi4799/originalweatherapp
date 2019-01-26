package com.example.tech2k8.weatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class WeatherMapFragment extends Fragment {

    private GoogleMap myMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootLayout=inflater.inflate(R.layout.weather_mapp_fragment,null,false);
        SupportMapFragment mapFragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                myMap=googleMap;
                Toast.makeText(getContext(), "map loaded", Toast.LENGTH_SHORT).show();
                sendReq();


            }
        });
        return rootLayout;
    }
    private void sendReq()
    {

        NetworkCalls networkCalls =new NetworkCalls(new NetworkCallsListener() {
            @Override
            public void onSuccess(ArrayList<WeatherData> data) {

                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                for (int i=0;i<data.size();i++)
                {
                    double lat = Double.parseDouble(data.get(i).getLatitude());
                    double lon = Double.parseDouble(data.get(i).getLongitude());

                    LatLng latLng =new LatLng(lat,lon);

                    MarkerOptions options =new MarkerOptions();
                    options.position(latLng);
                    options.title(data.get(i).getTemperature());

                    myMap.addMarker(options);
                }
            }

            @Override
            public void onFailure() {

                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        networkCalls.loadDataFromNetwork(getContext());
    }
}
