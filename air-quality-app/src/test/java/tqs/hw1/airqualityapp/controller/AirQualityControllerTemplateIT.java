package tqs.hw1.airqualityapp.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.hw1.airqualityapp.models.CacheStats;
import tqs.hw1.airqualityapp.models.Results;
import tqs.hw1.airqualityapp.repository.CacheStatsRepository;
import tqs.hw1.airqualityapp.repository.ResultsRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class AirQualityControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ResultsRepository repository;

    @Autowired
    private CacheStatsRepository cacheStatsrepository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
        cacheStatsrepository.deleteAll();
    }


    @Test
    public void whenGetResults_thenStatus200() {

        // repository is filled with info when api is called , because there is no create method for this web page ( the info is taken from external api )

        ResponseEntity<List<Results>> response = restTemplate
                .exchange("/tqs/get/Leiria", HttpMethod.GET, null, new ParameterizedTypeReference<List<Results>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void whenGetCacheStats_thenStatus200() {

        // repository is filled when api is called

        ResponseEntity<CacheStats> response = restTemplate
                .exchange("/tqs/get/cacheStats", HttpMethod.GET, null, new ParameterizedTypeReference<CacheStats>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }




}
