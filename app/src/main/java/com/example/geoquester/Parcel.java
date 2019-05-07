package com.example.geoquester;

import java.io.Serializable;

public class Parcel implements Serializable {
    private final String CITY;
    private boolean temperature;
    private boolean feelingTemperature;
    private boolean pressure;
    private boolean humidity;
    private boolean rainfall;
    private boolean windSpeed;
    private boolean windDirection;



    public Parcel(String city) {
        CITY = city;
    }

    public String getCITY() {
        return CITY;
    }

    public boolean isTemperature() {
        return temperature;
    }

    public void setTemperature(boolean temperature) {
        this.temperature = temperature;
    }

    public boolean isFeelingTemperature() {
        return feelingTemperature;
    }

    public void setFeelingTemperature(boolean feelingTemperature) {
        this.feelingTemperature = feelingTemperature;
    }

    public boolean isPressure() {
        return pressure;
    }

    public void setPressure(boolean pressure) {
        this.pressure = pressure;
    }

    public boolean isHumidity() {
        return humidity;
    }

    public void setHumidity(boolean humidity) {
        this.humidity = humidity;
    }

    public boolean isRainfall() {
        return rainfall;
    }

    public void setRainfall(boolean rainfall) {
        this.rainfall = rainfall;
    }

    public boolean isWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(boolean windSpeed) {
        this.windSpeed = windSpeed;
    }

    public boolean isWindDirection() {
        return windDirection;
    }

    public void setWindDirection(boolean windDirection) {
        this.windDirection = windDirection;
    }
}
