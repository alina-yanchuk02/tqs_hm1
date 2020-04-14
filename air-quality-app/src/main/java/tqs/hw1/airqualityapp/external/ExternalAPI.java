package tqs.hw1.airqualityapp.external;

import javax.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import tqs.hw1.airqualityapp.models.AirQualityResponse;

import java.io.IOException;


@Component
public class ExternalAPI{

    private RestTemplate template;
    ObjectMapper mapper = null;
    HttpHeaders headers = null;



    @PostConstruct
    public void init() {
        template = new RestTemplate();
        mapper = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        template.getMessageConverters().add(new StringHttpMessageConverter());
    }


    public AirQualityResponse getInfoForCity(String city) throws AirQualityException {

        // get info for city from external API

        String uRL = "https://api.openaq.org/v1/measurements?city="+city;
        String result = template.getForObject(uRL, String.class);

        AirQualityResponse response = null;

        try {

            response = mapper.readValue(result, AirQualityResponse.class);

        } catch (IOException e) {

            throw new AirQualityException("API OPENAQ Service Failed..Try Again !");

        }

        return response;
    }




}
