package tqs.hw1.airqualityapp.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import tqs.hw1.airqualityapp.external.ExternalAPI;
import tqs.hw1.airqualityapp.models.AirQualityResponse;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ExternalAPITest {

    @Mock( lenient = true)
    private RestTemplate restTemplate;

    @Mock( lenient = true)
    private ObjectMapper objectMapper;

    @InjectMocks
    private ExternalAPI externalApi;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void consumeExternalApiTest() throws Exception {

        externalApi.init();

        AirQualityResponse response = new AirQualityResponse();

        String result="AnyString";

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(result);

        Mockito.when(objectMapper.readValue(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(response);

        assertThat(externalApi.getInfoForCity("Leiria")).isInstanceOf(AirQualityResponse.class);

    }




    @Test
    public void testGetMessage() {

        restTemplate=new RestTemplate();

        String response=restTemplate.getForObject("https://api.openaq.org/v1/measurements?city=Leiria", String.class);

        assertThat(response).contains("Leiria").contains("results");

    }

}
