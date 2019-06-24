package com.geekbrains.vadimshiryaev.weatherapplication.model;

import android.content.Context;

import com.geekbrains.vadimshiryaev.weatherapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class CityLab {
    private static CityLab cityLab;
    private static Context context;

    private List<City> cities;
    private HashMap<UUID, City> citiesMap;

    private Random random = new Random();

    public static CityLab getCityLab(Context context){
        if (cityLab == null) {
            cityLab = new CityLab(context);
        }
        return cityLab;
    }

    public static CityLab getNewCityLab(Context context){
        cityLab = new CityLab(context);
        return cityLab;
    }

    public CityLab(Context context) {
        cities = new ArrayList<>();
        citiesMap = new HashMap<>();
        this.context = context;
        String[] citiesNames = context.getResources().getStringArray(R.array.cities);
        for (int i = 0; i < citiesNames.length; i++) {
            City city = new City();
            String[] cityNameAndCountry = citiesNames[i].split(":");
            city.setName(cityNameAndCountry[0]);
            city.setCountry(cityNameAndCountry[1]);
            WeekLab weekLab = WeekLab.getWeekLab(context);
            city.setWeek(weekLab.getDays());
            city.setWeekMap(weekLab.getDayMap());
            cities.add(city);
            citiesMap.put(city.getId(), city);
        }
    }



    public List<City> getCities() {
        return cities;
    }

    public City getCity(UUID id) {
        return citiesMap.get(id);
    }

    public void removeCity(UUID id) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getId().equals(id)) {
                cities.remove(cities.get(i));
            }
        }

    }


    public void addCity() {
        String[] additionalCitiesNames = context.getResources().getStringArray(R.array.additional_cities);
        City city = new City();
        String[] cityNameAndCountry = additionalCitiesNames[random.nextInt(additionalCitiesNames.length)].split(":");
        city.setName(cityNameAndCountry[0]);
        city.setCountry(cityNameAndCountry[1]);
        WeekLab weekLab = WeekLab.getWeekLab(context);
        city.setWeek(weekLab.getDays());
        city.setWeekMap(weekLab.getDayMap());
        cities.add(city);
        citiesMap.put(city.getId(), city);
    }


    }

