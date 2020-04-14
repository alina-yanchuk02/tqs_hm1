package tqs.hw1.airqualityapp.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirQualityResponseTest {

    private AirQualityResponse response;



    @BeforeEach
    public void setUp() {
        Results result=new Results("PT0","o3",30.0,"ug/m","PT","Leiria");
        List<Results> lista= Arrays.asList(result);
        response=new AirQualityResponse(lista);

    }

    @AfterEach
    public void tearDown(){
        response = null;
    }


    @Test
    public void testGetResults() {

        Results results=new Results("PT0","o3",30.0,"ug/m","PT","Leiria");
        List<Results> lista= Arrays.asList(results);

        List<Results> expResult=lista;
        List<Results> result=response.getResults();
        assertEquals(expResult.get(0).getCity(), result.get(0).getCity());
    }

    @Test
    public void testSetResults() {
        Results results=new Results("PT0","o3",30.0,"ug/m","PT","Leiria");
        List<Results> lista= Arrays.asList(results);

        response.setResults(lista);
        assertEquals(lista, response.getResults());
    }

}
