package com.weatherly.demo.services;


import org.junit.Assert;
import org.junit.Test;


/*
    Backend Unit tests
 */
public class UnitTests {


    @Test
    public void weatherTestLocation(){
        Weather weather = new Weather(new Location("Estonia", "Tartumaa", "Tartu"));

        Assert.assertNotEquals("", weather.getPrecipitation());
        Assert.assertNotEquals(null, weather.getTemp());
        Assert.assertNotEquals(null, weather.getWindSpeed());
        Assert.assertNotEquals("", weather.getWindDirection());
        Assert.assertNotEquals("", weather.toString());
        Assert.assertNotEquals("", weather.getWeatherState());


    }

    @Test
    public void weatherTestLatLong(){
        Location location = new Location("193.40.12.10");
        Weather weather = new Weather(location.getLatitude(), location.getLongitude());

        Assert.assertNotEquals("tundmatu", weather.getPrecipitation());
        Assert.assertNotEquals("tundmatu", weather.getWeatherState());
        Assert.assertNotEquals("tundmatu", weather.getWindDirection());
        Assert.assertNotEquals("", weather.toString());
        Assert.assertNotEquals(null, weather.getTemp());
        Assert.assertNotEquals(null, weather.getWindSpeed());

    }

    @Test
    public void LocationTestIP(){
        Location location = new Location("193.40.12.10");

        Assert.assertEquals("58.3661", location.getLatitude());
        Assert.assertEquals("26.7361", location.getLongitude());

        Assert.assertEquals("Tartu", location.getRegionName());
        Assert.assertEquals("Tartu", location.getCity());


        Assert.assertEquals("EE", location.getCountryCode());
        Assert.assertEquals("193.40.12.10", location.getIpAadress());

    }


    @Test
    public void LocationTestParameters(){
        Location location = new Location("Germany", "Berlin", "Berlin");

        Assert.assertEquals("Germany", location.getCountry());
        Assert.assertEquals("Berlin", location.getRegionName());
        Assert.assertEquals("Berlin", location.getCity());

    }



}
