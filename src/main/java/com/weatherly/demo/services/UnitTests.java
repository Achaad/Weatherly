package com.weatherly.demo.services;


import org.junit.Assert;
import org.junit.Test;


/*
    Backend Unit tests
 */
public class UnitTests {


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
