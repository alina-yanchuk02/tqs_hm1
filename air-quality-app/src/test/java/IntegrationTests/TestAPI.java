package IntegrationTests;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class TestAPI {


    // For this tests, the application needs to be running


    @Test
    public void getAirQualityCityInfo_returns200_andExpectedInfo() {

        String uriBase = "http://localhost:8080/tqs/get/Leiria";

        given()
                .relaxedHTTPSValidation()
                .when()
                .get(uriBase)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].unit", is("µg/m³"))
                .body("[0].country", is("PT"))
                .body("[0].city", is("Leiria"));

    }

    @Test
    public void getCacheStats_returns200_andExpectedInfo() {

        String uriBase = "http://localhost:8080/tqs/get/cacheStats";

        given()
                .relaxedHTTPSValidation()
                .when()
                .get(uriBase)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(1))
                .body("numberOfRequests", isA(int.class))
                .body("numberOfHits", isA(int.class))
                .body("numberOfMisses", isA(int.class));


    }

    @Test
    public void getAirQualityPage_returns200() {

        String uriBase = "http://localhost:8080/tqs/airQualityPT";

        given()
                .relaxedHTTPSValidation()
                .when()
                .get(uriBase)
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML);

    }

    @Test
    public void getCacheStatsPage_returns200() {

        String uriBase = "http://localhost:8080/tqs/cacheStats";

        given()
                .relaxedHTTPSValidation()
                .when()
                .get(uriBase)
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML);

    }



}
