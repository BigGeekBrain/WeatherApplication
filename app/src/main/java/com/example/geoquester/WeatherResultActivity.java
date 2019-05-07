package com.example.geoquester;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class WeatherResultActivity extends AppCompatActivity {

    private TextView city;
    private TextView temperature;
    private TextView fellingTemperature;
    private TextView pressure;
    private TextView humidity;
    private TextView rainfall;
    private TextView windSpeed;
    private TextView windDirection;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        city = findViewById(R.id.requestCity);
        temperature = findViewById(R.id.temperatureView);
        fellingTemperature = findViewById(R.id.fellingTemperatureView);
        pressure = findViewById(R.id.pressureView);
        humidity = findViewById(R.id.humidityView);
        rainfall = findViewById(R.id.rainfallView);
        windSpeed = findViewById(R.id.windSpeedView);
        windDirection = findViewById(R.id.windDirectionView);

        Parcel parcel = (Parcel) getIntent().getExtras().getSerializable("CITY");
        city.setText(parcel.getCITY());

        if (parcel.isTemperature()) temperature.setVisibility(View.VISIBLE);
        if (parcel.isFeelingTemperature()) fellingTemperature.setVisibility(View.VISIBLE);
        if (parcel.isPressure()) pressure.setVisibility(View.VISIBLE);
        if (parcel.isHumidity()) humidity.setVisibility(View.VISIBLE);
        if (parcel.isRainfall()) rainfall.setVisibility(View.VISIBLE);
        if (parcel.isWindSpeed()) windSpeed.setVisibility(View.VISIBLE);
        if (parcel.isWindDirection()) windDirection.setVisibility(View.VISIBLE);

    }
}
