package com.example.tech2k8.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkCalls {

    NetworkCallsListener listener;

    public NetworkCalls(NetworkCallsListener listener) {
        this.listener = listener;
    }

    public void loadDataFromNetwork(Context context) {
    String url = "https://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b6907d289e10d714a6e88b30761fae22";
        Log.i("FragmentListWeatherData","started to load url");
    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            Log.i("FragmentListWeatherData", "success response :" + response);

            extractDataFromJson(response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            //Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            listener.onFailure();
            Log.i("FragmentListWeatherData", "error response " + error.getMessage());
        }
    });
        Log.i("FragmentListWeatherData","request is added to queue");
        Volley.newRequestQueue(context).add(request);
}

    private void extractDataFromJson(String urlReqResult)
    {
        ArrayList<WeatherData> weatherDataArrayList =new ArrayList<>();
        try {
            JSONObject rootObject =new JSONObject(urlReqResult);
            JSONArray listArray = rootObject.getJSONArray("list");
            for (int i=0;i<listArray.length();i++)
            {
                JSONObject indivJsonObj=listArray.getJSONObject(i);
                JSONObject coordObj=indivJsonObj.getJSONObject("coord");
                String lattitude = coordObj.getString("lat");
                String longitude =coordObj.getString("lon");

                JSONObject mainObj = indivJsonObj.getJSONObject("main");
                String temperature = mainObj.getString("temp");
                WeatherData data =new WeatherData();
                data.setLatitude(lattitude);
                data.setLongitude(longitude);
                data.setTemperature(temperature);
                weatherDataArrayList.add(data);
            }

//            WeatherDataAdapter adapter =new WeatherDataAdapter(weatherDataArrayList,getContext());
//            weatherDataInUi.setAdapter(adapter);
            listener.onSuccess(weatherDataArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


