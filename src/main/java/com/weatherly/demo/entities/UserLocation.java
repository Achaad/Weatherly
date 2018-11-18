package com.weatherly.demo.entities;

import javax.persistence.Embeddable;


/**
 * An embeddable class for storing a location that will be used for querying weather
 */
@Embeddable
public class UserLocation {

    private String location;

    private String country;

    private String region;

    private String city;

    public UserLocation() {
    }

    public UserLocation(String location) {
        String[] parts = location.split(",");
        this.location = location;
        this.country = parts[0];
        this.region = parts[1];
        this.city = parts[2];
    }

    public UserLocation(String location, String country, String region, String city) {
        this.location = location;
        this.country = country;
        this.region = region;
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
