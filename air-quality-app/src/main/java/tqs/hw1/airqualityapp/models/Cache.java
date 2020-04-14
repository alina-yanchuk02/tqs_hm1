package tqs.hw1.airqualityapp.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

@Entity
@Table(name = "cache")
@Getter
@Setter
public class Cache implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long timeToLive;

    private Long startTime;

    private HashMap<String, AirQualityResponse> cacheMap;

    public Cache(long timeToLive) {

        this.cacheMap = new HashMap<>();
        this.timeToLive= timeToLive;
        this.startTime = System.currentTimeMillis();

    }


    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public HashMap<String, AirQualityResponse> getCacheMap() {
        return cacheMap;
    }



    // CACHE METHODS

    public void put(String key, AirQualityResponse value) {

       this.startTime = System.currentTimeMillis();
       cacheMap.put(key, value);

    }

    public AirQualityResponse get(String key) {
        return cacheMap.get(key);
    }


    public void remove(String key) {

            cacheMap.remove(key);

    }


    public int size() {
        cleanup();
        return cacheMap.size();

    }


    public void cleanup() {

        long now = System.currentTimeMillis();

        if((now-this.startTime)>this.timeToLive) {
            cacheMap.clear();
             this.startTime = System.currentTimeMillis();
        }

    }



}
