package com.kh_sof_dev.real_estate_bosnia.Activities.Classes;

import com.kh_sof_dev.real_estate_bosnia.R;

public class location {
    private Double lat ,lng;
    private String city= "لم يتم تحديد المكان";

    public location(Double lat, Double lng, String city) {
        this.lat = lat;
        this.lng = lng;
        this.city = city;
    }

    public location() {

    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
