package tqs.hw1.airqualityapp.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.hw1.airqualityapp.SpringBootAirQualityCheckAppApplication;
import tqs.hw1.airqualityapp.repository.CacheStatsRepository;
import tqs.hw1.airqualityapp.repository.ResultsRepository;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootAirQualityCheckAppApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AirQualityControllerIT {

    @Autowired
    private MockMvc mvc;

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
    public void whenGetResults_thenStatus200() throws Exception {

        // repository is filled with info when api is called , because there is no create method for this web page ( the info is taken from external api )
        // as the external Api has always info for city Leiria ,
        // the list of results will have size 1 ou more ( the results for one city differ by the parameter ( co2, o2, .... but the city is the same )

        mvc.perform(get("/tqs/get/Leiria").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].city", is("Leiria")));

    }



    // FOR THIS TEST TO WORK, IT NEED TO BE RUNNED ALONE, WITHOUT THE PREVIOUS TEST, BECAUSE IN THAT CASE, THE CACHESTATS WILL CHANGE ,
    // AS THE API FOR CITY LEIRIA IS CALLED; AND NUMBER OF REQUESTS WILL BE 1 AND NOT 0

    @Test
    public void whenGetCacheStats_thenStatus200() throws Exception {

        // repository is filled when api is called , always only with one instance


        mvc.perform(get("/tqs/get/cacheStats").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("numberOfRequests", is(0)));

    }


}

