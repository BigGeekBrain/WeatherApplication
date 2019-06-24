package com.geekbrains.vadimshiryaev.weatherapplication.model;


import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Day {

    private String temperatureDegree;
    private int humidity;
    private int windSpeed;
    private String windDirection;
    private String weather;
    private Date date;
    private Drawable weatherLabel;
    private UUID id;

    public Day() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getTemperatureDegree() {
        return temperatureDegree;
    }

    public Drawable getWeatherLabel() {
        return weatherLabel;
    }

    public void setWeatherLabel(Drawable weatherLabel) {
        this.weatherLabel = weatherLabel;
    }

    public void setTemperatureDegree(String temperatureDegree) {
        this.temperatureDegree = temperatureDegree;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM", Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
