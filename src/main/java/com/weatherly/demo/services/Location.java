package com.weatherly.demo.services;

import java.net.MalformedURLException;
import java.net.URL;

public class Location {

    private String ipAadress;


    private String API_KEY = "389c528940c93f77df3188db4102d580";
    private String apiAadress = "http://api.ipstack.com/";

    private XML xml;

    private String longitude;
    private String latitude;

    private String countryCode;

    private String country;
    private String regionName;

    private String city;

    //Konstruktor juhuks, kui IP abil tuleb vales formaadis asukoht.
    public Location(String country, String regionName, String city){
        this.country = country;
        this.regionName = regionName;
        this.city = city;
    }
    //Konstruktor, mis võtab andmed IP aadressi järgi
    public Location(String ipAadress) {
        this.ipAadress = ipAadress;

        try {

            xml = new XML(new URL(apiAadress + ipAadress + "?access_key=" + API_KEY+"&output=xml"));
            latitude = xml.getUnNestedTagContent("latitude");
            longitude = xml.getUnNestedTagContent("longitude");

            country = xml.getUnNestedTagContent("country_name");
            countryCode = xml.getUnNestedTagContent("country_code");
            regionName = xml.getUnNestedTagContent("region_name");

            city = xml.getUnNestedTagContent("city");


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "Location{" +
                "ipAadress='" + ipAadress + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", country='" + country + '\'' +
                ", regionName='" + regionName + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getIpAadress() {
        return ipAadress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLongitude() {
        return longitude;
    }



    public String getLatitude() {
        return latitude;
    }



    public String getCountry() {
        return country;
    }

    public String getRegionName() {
        return regionName;
    }



    public String getCity() {
        return city;
    }

}