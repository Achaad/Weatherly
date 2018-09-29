package com.weatherly.demo.services;


import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Weather {


    //Klass, mis tagastab ilmaelemente mingi "Location" objekti kohta
    /*
     *
     * N�idislink XML: http://www.yr.no/place/Estonia/Harjumaa/Tallinn/forecast.xml
     */

    private Location location;
    private XML xml;

    private double temp;     //temperatuur
    private double windSpeed; //Tuulekiirus
    private String weatherState; //ilmaolukord, pilvine/p�ikseline jne.
    private String windDirection; // tuulesuund
    private String precipitation; // sademed



    public Weather(Location location) {

        this.location = location;
        try {

            xml = new XML(new URL("http://www.yr.no/place/" + location.getCountry() + "/"+ location.getRegionName() + "/"+ location.getCity() + "/forecast.xml"));

            weatherState = xml.getTagContentValue("symbol", "name");
            temp = Double.parseDouble(xml.getTagContentValue("temperature", "value"));
            windSpeed = Double.parseDouble(xml.getTagContentValue("windSpeed", "mps"));
            windDirection = xml.getTagContentValue("windDirection", "code");
            precipitation = xml.getTagContentValue("precipitation", "value");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Weather(String latitude, String longitude){
        try {

            xml = new XML(new URL("https://weather.cit.api.here.com/weather/1.0/report.xml?product=observation&latitude=" + latitude + "&longitude=" + longitude + "&oneobservation=true&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg"));


           // System.out.println("https://weather.cit.api.here.com/weather/1.0/report.xml?product=observation&latitude=" + latitude + "&longitude=" + longitude + "&oneobservation=true&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg");
            weatherState = xml.getUnNestedTagContent("description");
            temp = Double.parseDouble(xml.getUnNestedTagContent("temperature"));
            windSpeed = Double.parseDouble(xml.getUnNestedTagContent("windSpeed"));
            windDirection = xml.getUnNestedTagContent("windDescShort");
            precipitation = xml.getUnNestedTagContent("precipitation1H");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Location getLocation() {
        return location;
    }


    public double getTemp() {
        return temp;
    }


    public double getWindSpeed() {
        return windSpeed;
    }


    public String getWeatherState() {
        return weatherState;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void printWeatherNow() {
        System.out.println("Ilm hetkel:");

        // Prindib välja ilma näitajad
        System.out.println(getWeatherState());
        System.out.println("Temperatuur:  " + getTemp() + "\u00b0C");
        System.out.println("Tuulesuund: " + getWindDirection());
        System.out.println("Tuulekiirus: " + getWindSpeed() + " m/s");
        System.out.println("Sademed: " + getPrecipitation() + " mm");
        //System.out.println(weather.getLocation()); Minu arust tagastab liiga palju infot tava kasutajale
    }

    // Miskipärast ei soovi töötada ilma "throws ParseException"
    public void printWeatherHourly() throws ParseException {

        // Leiame indeksi, milleni näitame andmed
        String[] endTimes = xml.getAllTagContentValues("time", "to");
        String[] startTimes = xml.getAllTagContentValues("time", "from");
        int endIndex = 0;

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);
        Date dayLater = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (String time : endTimes) {
            Date d = sdf.parse(time);

            if (d.before(dayLater)) {
                endIndex++;
            }
        }

        // Nüüd printime välja ilm
        String[] staatused = xml.getAllTagContentValues("symbol", "name");
        String[] temperatuurid = xml.getAllTagContentValues("temperature", "value");
        String[] kiirused = xml.getAllTagContentValues("windSpeed", "mps");
        String[] suunad = xml.getAllTagContentValues("windDirection", "code");
        String[] sademed = xml.getAllTagContentValues("precipitation", "value");

        for(int i = 0; i <= endIndex; i++) {
            System.out.println();
            System.out.println("Aeg: " + sdf.parse(startTimes[i]) + " - " + sdf.parse(endTimes[i]));
            System.out.println(staatused[i]);
            System.out.println("Temperatuur:  " + Double.parseDouble(temperatuurid[i]) + "\u00b0C");
            System.out.println("Tuulesuund: " + suunad[i]);
            System.out.println("Tuulekiirus: " + Double.parseDouble(kiirused[i]) + " m/s");
            System.out.println("Sademed: " + Double.parseDouble(sademed[i]) + " mm");
        }
    }

    public void printWeatherForWeek() throws ParseException {

        // Leiame indeksid, mille kohta näitame andmed
        String[] endTimes = xml.getAllTagContentValues("time", "to");
        String[] startTimes = xml.getAllTagContentValues("time", "from");
        String[] timePeriods = xml.getAllTagContentValues("time", "period");

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);
        Date endDate = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        // Leiame kõik indeksid, mis vastavad lõunale või südaööle
        int index = 0;
        ArrayList<Integer> indexes = new ArrayList<>();
        for (String period : timePeriods) {
            if (period.equals("2") || period.equals("0")) {
                indexes.add(index);
            }
            index++;
        }

        // Jätame ainuld need indeksid, mis on enne nädala
        ArrayList<Integer> finalIndexes = new ArrayList<>();
        for (int i = 0; i < indexes.size(); i++) {
            if(sdf.parse(endTimes[indexes.get(i)]).before(endDate)) {
                finalIndexes.add(indexes.get(i));
            }
        }

        // Nüüd printime välja ilm
        String[] staatused = xml.getAllTagContentValues("symbol", "name");
        String[] temperatuurid = xml.getAllTagContentValues("temperature", "value");
        String[] kiirused = xml.getAllTagContentValues("windSpeed", "mps");
        String[] suunad = xml.getAllTagContentValues("windDirection", "code");
        String[] sademed = xml.getAllTagContentValues("precipitation", "value");

        SimpleDateFormat onlyDate = new SimpleDateFormat("dd.MM.yyyy");
        for(int i = 0; i < finalIndexes.size(); i++) {
            int k = finalIndexes.get(i);
            System.out.println();
            if (timePeriods[k].equals("2")) {
                System.out.println("Aeg: " + onlyDate.format(sdf.parse(startTimes[k])) + " päev.");
            } else {
                System.out.println("Aeg: " + onlyDate.format(sdf.parse(startTimes[k])) + " öö.");
            }

            System.out.println(staatused[k]);
            System.out.println("Temperatuur:  " + Double.parseDouble(temperatuurid[k]) + "\u00b0C");
            System.out.println("Tuulesuund: " + suunad[k]);
            System.out.println("Tuulekiirus: " + Double.parseDouble(kiirused[k]) + " m/s");
            System.out.println("Sademed: " + Double.parseDouble(sademed[k]) + " mm");
        }
    }

    @Override
    public String toString() {
           return getWeatherState() + "\n" +
                   getTemp() + "\u00b0C" + "\n" +
                   "Tuulesuund: " + getWindDirection() + "\n" +
                   "Tuulekiirus: " + getWindSpeed() + " m/s" + "\n" +
                   "Sademed: " + getPrecipitation() + " mm" + "\n";
        }

}
