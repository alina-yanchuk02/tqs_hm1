package tqs.hw1.airqualityapp.models;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cacheStats")
@Getter
@Setter
public class CacheStats implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int numberOfRequests;

    private int numberOfHits;

    private int numberOfMisses;

    public CacheStats() {
        this.numberOfHits=0;
        this.numberOfRequests=0;
        this.numberOfMisses=0;
    }

    public CacheStats(int numberOfRequests, int numberOfHits, int numberOfMisses) {
        this.numberOfRequests = numberOfRequests;
        this.numberOfHits = numberOfHits;
        this.numberOfMisses = numberOfMisses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(int numberOfHits) {
        this.numberOfHits = numberOfHits;
    }

    public int getNumberOfMisses() {
        return numberOfMisses;
    }

    public void setNumberOfMisses(int numberOfMisses) {
        this.numberOfMisses = numberOfMisses;
    }

}
