package tqs.hw1.airqualityapp.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "results")
@Getter
@Setter
public class Results implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private String parameter;

    private Double value;

    @OneToOne(cascade = {CascadeType.ALL})
    private Coordinates coordinates;

    @OneToOne(cascade = {CascadeType.ALL})
    private Date date;

    private String unit;

    private String country;

    private String city;

    public Results(){
        // need for Json mapping
    }


    public Results(String location, String parameter, Double value, String unit, String country, String city) {
        this.location = location;
        this.parameter = parameter;
        this.value = value;
        this.coordinates=null;
        this.date=null;
        this.unit = unit;
        this.country = country;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public String getParameter() {
        return parameter;
    }


}
