package com.example.covid_19healthcare;

public class covidLoc {
    double latitude;
    double longitude;
    String name,id;
    int res;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public covidLoc(double latitude, double longitude, String name, String id, int res) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name=name;
        this.id=id;
        this.res=res;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
