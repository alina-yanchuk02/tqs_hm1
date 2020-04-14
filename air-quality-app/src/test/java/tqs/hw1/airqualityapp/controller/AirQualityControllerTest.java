package tqs.hw1.airqualityapp.controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import tqs.hw1.airqualityapp.models.*;
import tqs.hw1.airqualityapp.external.AirQualityException;
import tqs.hw1.airqualityapp.services.AirQualityService;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AirQualityControllerTest {

    @Mock( lenient = true)
    AirQualityService service;

    @Mock( lenient = true)
    private Model model;


    @InjectMocks
    AirQualityController controller;


    @Test
    public void testGetAirQualityCityInfo() throws AirQualityException {

        Results resultA=new Results("PT0","o3",30.0,"ug/m","PT","Leiria");
        Results resultB=new Results("PT0","co2",15.0,"ug/m","PT","Leiria");

        List<Results> lista = Arrays.asList(resultA,resultB);


        Mockito.doNothing().when(service).getInfoForCityAndSave("Leiria");
        Mockito.when(service.findAllResults()).thenReturn(lista);


        assertEquals(controller.getAirQualityCityInfo("Leiria"),lista);

    }

    @Test
    public void testGetCacheStats() {

        CacheStats cacheStats = new CacheStats();

        Mockito.when(service.cacheStatsSave()).thenReturn(cacheStats);

        assertEquals(controller.getCacheStats(),cacheStats);
    }


    @Test
    public void testGetAirQualityPage() {

        final String model = "airQualityPT";

        assertEquals(model, controller.getAirQualityPage());

    }

    @Test
    public void testGetCacheStatsPage() {

        CacheStats cacheStats=new CacheStats();

        Mockito.when(service.cacheStatsSave()).thenReturn(cacheStats);

        String returnValue = controller.getCacheStatsPage(model);

        verify(model, times(1)).addAttribute("requests", cacheStats.getNumberOfRequests());
        verify(model, times(1)).addAttribute("hits", cacheStats.getNumberOfHits());
        verify(model, times(1)).addAttribute("misses", cacheStats.getNumberOfMisses());

        assertEquals("cacheStats", returnValue);
    }
}
