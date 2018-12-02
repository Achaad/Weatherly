package com.weatherly.demo.services;


import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/*
    Back and frontend Unit tests
 */
public class UnitTests {

    // Check that stats canvas is being drawn properly
    @Test
    public void statisticsTest(){

        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080/");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement stats = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"nav-mobile\"]/li[2]/a")));

        stats.click();

        WebElement canvas = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"browserCanvas\"]")));

        Assert.assertEquals(400, canvas.getSize().width);
        Assert.assertEquals(400, canvas.getSize().height);

        driver.quit();
    }

    @Test
    public void loginTest(){

        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement login = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"nav-mobile\"]/li[3]/a")));

        login.click();

        WebElement googleLogin = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/p[1]/a")));

        googleLogin.click();


        //

        WebElement loginContent = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"headingText\"]/content")));

        Assert.assertEquals("logige sisse", loginContent.getText().toLowerCase());

        driver.quit();
    }

    // Test if language changes correctly
    @Test
    public void languageTest(){
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement language = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"language_dropdown_button\"]/span")));

        language.click();

        // //*[@id="language_dropdown"]/li[2]/a
        WebElement estonian = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"language_dropdown\"]/li[2]/a")));
        estonian.click();


        WebElement ilm = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tere\"]")));
        WebElement temp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"index-banner\"]/div/div[2]/div[1]/h5")));

        Assert.assertEquals("praegune ilm: tundmatu",ilm.getText().toLowerCase());
        Assert.assertEquals("temperatuur", temp.getText().toLowerCase());

        driver.quit();

    }

    // Tests if the searching for Tartu routes you correctly and displays the right location data.
    @Test
    public void searchWeatherTest(){
        WebDriver driver = new ChromeDriver();

         driver.get("http://localhost:8080/");

        ((ChromeDriver) driver).executeScript("window.resizeTo(1024, 768);");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement searchbar = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("search")));

        searchbar.sendKeys("Estonia,Tartumaa,Tartu");

        WebElement button = driver.findElement(By.xpath("//*[@id=\"searchBar\"]/div/i"));
        button.click();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        WebElement location = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"index-banner\"]/div/div[3]/p")));

        Assert.assertEquals("your location: tartu, estonia, tartumaa", location.getText().toLowerCase());

        driver.quit();

    }

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
