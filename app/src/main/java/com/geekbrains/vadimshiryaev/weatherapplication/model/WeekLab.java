package com.geekbrains.vadimshiryaev.weatherapplication.model;

import android.content.Context;

import com.geekbrains.vadimshiryaev.weatherapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WeekLab {

    private List<Day> days;
    private HashMap<UUID, Day> dayMap;

    private Random random = new Random();

    public static WeekLab getWeekLab(Context context) {
        return new WeekLab(context);
    }

    public WeekLab(Context context) {
        days = new ArrayList<>();
        dayMap = new HashMap<>();
        Calendar calendar = new GregorianCalendar();
        for (int i = 0; i < 7; i++) {
            Day day = new Day();
            day.setDate(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            day.setHumidity(random.nextInt(100));
            day.setWindSpeed(random.nextInt(10));
            setWindDirection(day);
            setWeather(context, day);
            setTemperature(day);
            days.add(day);
            dayMap.put(day.getId(), day);
        }
    }

    private void setWindDirection(Day day) {
        switch (random.nextInt(8)) {
            case 0:
                day.setWindDirection("N");
                break;
            case 1:
                day.setWindDirection("S");
                break;
            case 2:
                day.setWindDirection("W");
                break;
            case 3:
                day.setWindDirection("E");
                break;
            case 4:
                day.setWindDirection("N-W");
                break;
            case 5:
                day.setWindDirection("S-W");
                break;
            case 6:
                day.setWindDirection("S-E");
                break;
            case 7:
                day.setWindDirection("N-E");
                break;
        }
    }

    private void setTemperature(Day day) {
        int temperature = random.nextInt(40);
        if (temperature == 0) {
            day.setTemperatureDegree(String.valueOf(temperature));
            return;
        }

        if ((random.nextInt(2) == 0)) {
            day.setTemperatureDegree("-" + temperature);
        } else {
            day.setTemperatureDegree("+" + temperature);
        }
    }

    private void setWeather(Context context, Day day) {
        if (random.nextInt(2) == 0) {
            day.setWeather(context.getResources().getString(R.string.weather_cloudy));
            day.setWeatherLabel(context.getResources().getDrawable(R.drawable.ic_cloud));
        } else {
            day.setWeather(context.getResources().getString(R.string.weather_sunny));
            day.setWeatherLabel(context.getResources().getDrawable(R.drawable.ic_sunny));
        }
    }

    public HashMap<UUID, Day> getDayMap(){
        return dayMap;
    }

    public List<Day> getDays() {
        return days;
    }
}

