package com.weatherly.demo.services;

import java.net.MalformedURLException;
import java.net.URL;

public class Location {

    private String ipAadress;

    private String apiAadress = "https://freegeoip.net/xml/";

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
        super();
        this.ipAadress = ipAadress;

        try {

            xml = new XML(new URL(apiAadress + ipAadress));
            latitude = xml.getUnNestedTagContent("Latitude");
            longitude = xml.getUnNestedTagContent("Longitude");

            country = xml.getUnNestedTagContent("CountryName");
            countryCode = xml.getUnNestedTagContent("CountryCode");
            regionName = xml.getUnNestedTagContent("RegionName");

            city = xml.getUnNestedTagContent("City");


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