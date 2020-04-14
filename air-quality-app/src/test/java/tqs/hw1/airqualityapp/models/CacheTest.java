package tqs.hw1.airqualityapp.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


public class CacheTest {

    private Cache cache;

    private AirQualityResponse response;

    @BeforeEach
    public void setUp() {
        cache = new Cache(100l);
        response = new AirQualityResponse();
    }

    @AfterEach
    public void tearDown(){
        cache = null;
    }

    @Test
    public void testSetStarTime() {
        Long startTime = 20l;
        cache.setStartTime(startTime);
        assertEquals(startTime, cache.getStartTime());
    }




    @Test
    public void testPut(){
        cache.put("A",response);
        assertTrue(cache.getCacheMap().containsKey("A"));
        assertEquals(1,cache.size());
    }

    @Test
    public void testCleanup() throws InterruptedException {

        cache.put("A",response);
        // to timeToLive pass
        cache.setStartTime(System.currentTimeMillis()-101l);
        cache.cleanup();
        assertEquals(0,cache.getCacheMap().size());
    }

    @Test
    public void testRemove(){
        cache.put("A",response);
        cache.remove("A");
        assertEquals(0,cache.getCacheMap().size());
    }

    @Test
    public void testSize(){
        cache.put("A",response);
        assertEquals(1,cache.size());
    }

    @Test
    public void testGet(){
        cache.put("A",response);
        assertEquals(response,cache.get("A"));
    }


}
