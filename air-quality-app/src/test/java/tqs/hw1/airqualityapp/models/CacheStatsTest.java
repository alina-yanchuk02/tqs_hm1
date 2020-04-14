package tqs.hw1.airqualityapp.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheStatsTest {

    private CacheStats cacheStats;


    @BeforeEach
    public void setUp() {
        cacheStats = new CacheStats(1, 2, 3);
    }

    @AfterEach
    public void tearDown(){
        cacheStats = null;
    }


    @Test
    public void testGetNumberOfRequests() {
        int expResult = 1;
        int result = cacheStats.getNumberOfRequests();
        assertEquals(expResult, result);
    }


    @Test
    public void testSetNumberOfRequests() {
        int numberOfRequests = 0;
        cacheStats.setNumberOfRequests(numberOfRequests);
        assertEquals(numberOfRequests, cacheStats.getNumberOfRequests());
    }


    @Test
    public void testGetNumberOfHits() {
        int expResult = 2;
        int result = cacheStats.getNumberOfHits();
        assertEquals(expResult, result);
    }


    @Test
    public void testSetNumberOfHits() {
        int numberOfHits = 0;
        cacheStats.setNumberOfHits(numberOfHits);
        assertEquals(numberOfHits, cacheStats.getNumberOfHits());
    }


    @Test
    public void testGetNumberOfMisses() {
        int expResult = 3;
        int result = cacheStats.getNumberOfMisses();
        assertEquals(expResult, result);
    }


    @Test
    public void testSetNumberOfMisses() {
        int numberOfMisses = 0;
        cacheStats.setNumberOfMisses(numberOfMisses);
        assertEquals(numberOfMisses, cacheStats.getNumberOfMisses());
    }



}





