package tqs.hw1.airqualityapp.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultsTest {

    private Results results;


    @BeforeEach
    public void setUp() {
        results=new Results("PT0","o3",30.0,"ug/m","PT","Leiria");
    }

    @AfterEach
    public void tearDown(){
        results = null;
    }


    @Test
    public void testGetParameter() {
        String expResult = "o3";
        String result = results.getParameter();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCity() {
        String expResult = "Leiria";
        String result = results.getCity();
        assertEquals(expResult, result);
    }


}
