package tqs.hw1.airqualityapp.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "airQualityResponse")
@Getter
@Setter
public class AirQualityResponse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Meta meta;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Results> results= new ArrayList<>();



    public AirQualityResponse(){
        // need for Json mapping
    }

    public AirQualityResponse(List<Results> results) {
        this.results = results;
    }



    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}

