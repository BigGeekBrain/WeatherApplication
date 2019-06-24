package com.geekbrains.vadimshiryaev.weatherapplication.model;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class City {
    private UUID id;
    private String name;
    private String country;
    private List<Day> week;
    private HashMap<UUID, Day> weekMap;

    public City() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {

        return id;
    }

    public HashMap<UUID, Day> getWeekMap() {
        return weekMap;
    }

    public void setWeekMap(HashMap<UUID, Day> weekMap) {
        this.weekMap = weekMap;
    }

    public List<Day> getWeek() {
        return week;
    }

    public void setWeek(List<Day> week) {
        this.week = week;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



}
