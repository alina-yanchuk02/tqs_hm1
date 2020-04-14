package FunctionalTests;

import org.junit.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumWebDriverTest {


    @Test
    public void testAirQualityPage() {

        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080/tqs/airQualityPT");

        assertEquals("Spring Boot - AirQualityPT", driver.getTitle());

        WebElement titulo=driver.findElement(By.id("titulo"));

        assertEquals("AirQuality in Portugal Today",titulo.getText().toString() );

        driver.findElement(By.id("input")).click();
        driver.findElement(By.id("input")).clear();
        driver.findElement(By.id("input")).sendKeys("Leiria");
        driver.findElement(By.id("input")).sendKeys(Keys.ENTER);

        // as info may take time to be processed ; may depend on internet speed
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableBody")));

        int numOfRow = table.findElements(By.tagName("tr")).size();

        assertThat(numOfRow).isGreaterThan(0);


        // when city doesn't exist, there are no results


        driver.findElement(By.id("input")).click();
        driver.findElement(By.id("input")).clear();
        driver.findElement(By.id("input")).sendKeys("DoesNotExist");
        driver.findElement(By.id("input")).sendKeys(Keys.ENTER);

        WebElement table2=driver.findElement(By.id("tableBody"));
        int numOfRow2 = table2.findElements(By.tagName("tr")).size();

        assertThat(numOfRow2).isEqualTo(0);

        driver.quit();

    }



    @Test
    public void testCacheStatsPage() {

        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080/tqs/cacheStats");

        assertEquals("Spring Boot - CacheStats", driver.getTitle());

        WebElement titulo=driver.findElement(By.id("titulo"));

        assertEquals("Cache Statistics of this session",titulo.getText().toString() );

        WebElement requests=driver.findElement(By.id("requests"));
        WebElement hits=driver.findElement(By.id("hits"));
        WebElement misses=driver.findElement(By.id("misses"));

        assertThat(Integer.parseInt(requests.getText())).isGreaterThanOrEqualTo(0);

        assertThat(Integer.parseInt(hits.getText())).isGreaterThanOrEqualTo(0);

        assertThat(Integer.parseInt(misses.getText())).isGreaterThanOrEqualTo(0);


        driver.quit();


    }


}



