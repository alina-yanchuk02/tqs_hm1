package tqs.hw1.airqualityapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.hw1.airqualityapp.external.ExternalAPI;
import tqs.hw1.airqualityapp.models.*;
import tqs.hw1.airqualityapp.repository.CacheStatsRepository;
import tqs.hw1.airqualityapp.repository.ResultsRepository;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AirQualityServiceUnitTest {

    @Mock( lenient = true)
    private ResultsRepository resultsRepository;

    @Mock( lenient = true)
    private CacheStatsRepository cacheStatsRepository;

    @Mock( lenient = true)
    private ExternalAPI externalApi;

    @Mock( lenient = true)
    private Cache cache;


    @InjectMocks
    private AirQualityService service;

    @BeforeEach
    public void setUp() {

        Results resultA=new Results("PT0","o3",30.0,"ug/m","PT","Leiria");
        Results resultB=new Results("PT0","co2",15.0,"ug/m","PT","Leiria");

        List<Results> lista = Arrays.asList(resultA,resultB);

        AirQualityResponse response = new AirQualityResponse(lista);

        CacheStats cacheStats = new CacheStats();

        List<CacheStats> lista2 = Arrays.asList(cacheStats);

        Mockito.when(resultsRepository.findByCity(resultA.getCity())).thenReturn(resultA);
        Mockito.when(resultsRepository.findByCity(resultB.getCity())).thenReturn(resultB);
        Mockito.when(resultsRepository.findByCity("DoesNotExist")).thenReturn(null);
        Mockito.when(resultsRepository.findAll()).thenReturn(lista);
        Mockito.when(resultsRepository.findByCityAndParameter(resultA.getCity(),resultA.getParameter())).thenReturn(resultA);
        Mockito.when(resultsRepository.findByCityAndParameter(resultB.getCity(),resultB.getParameter())).thenReturn(resultB);
        Mockito.when(cacheStatsRepository.findById(1l)).thenReturn(cacheStats);
        Mockito.when(resultsRepository.save(resultA)).thenReturn(resultA);
        Mockito.when(cacheStatsRepository.save(cacheStats)).thenReturn(cacheStats);
        Mockito.when(cacheStatsRepository.findAll()).thenReturn(lista2);


    }

    @Test
    public void whenValidCity_thenResultsShouldBeFound(){
        String city="Leiria";
        Results found = service.findInfoByCity(city);
        assertThat(found.getCity()).isEqualTo(city);

    }

    @Test
    public void whenInvalidCity_thenResultsShouldNotBeFound(){
        Results fromDb = service.findInfoByCity("DoesNotExist");
        assertThat(fromDb).isNull();

        verifyFindByCityIsCalledOnce("DoesNotExist");
    }


    @Test
    public void whenValidId_thenCacheStatsShouldBeFound(){
        CacheStats fromDb = service.findCacheStats(1l);
        assertThat(fromDb.getNumberOfRequests()).isEqualTo(0);

    }

    @Test
    public void whenInvalidId_thenCacheStatsShouldNoTbeFound(){
        CacheStats fromDb = service.findCacheStats(-10l);
        assertThat(fromDb).isNull();

        verifyFindByIdIsCalledOnce();

    }


    @Test
    public void given2Results_whenGetAll_thenReturn2Records(){

        Results resultA=new Results("PT0","o3",30.0,"ug/m","PT","Leiria");
        Results resultB=new Results("PT0","co2",15.0,"ug/m","PT","Leiria");

        List<Results> allResults = service.findAllResults();

        assertThat(allResults)
                .hasSize(2)
                .extracting(Results::getCity).contains(resultA.getCity(),resultB.getCity());

        verifyFindAllResultsIsCalledOnce();
    }

    @Test
    public void given1CacheStat_whenGetAll_thenReturn1Record(){
        CacheStats cacheStat = new CacheStats();

        List<CacheStats> allCacheStats = service.findAllCacheStats();

        assertThat(allCacheStats)
                .hasSize(1)
                .extracting(CacheStats::getNumberOfRequests).contains(cacheStat.getNumberOfRequests());

        verifyFindAllCacheStatsIsCalledOnce();
    }

    @Test
    public void whenSaveCacheStats_thenCacheStatsShouldBeSaved(){

        service.cacheStatsSave();
        assertThat(service.findAllCacheStats()).hasSize(1);

    }

    @Test
    public void testGetInfoForCityAndSave_withCacheAndWithoutCache() throws Exception {


        Results resultA=new Results("PT0","o3",30.0,"ug/m","PT","Leiria");
        Results resultB=new Results("PT0","co2",15.0,"ug/m","PT","Leiria");

        List<Results> lista = Arrays.asList(resultA,resultB);

        AirQualityResponse response = new AirQualityResponse(lista);

        // the cache is empty, so the external API is gonna be called once
        Mockito.when(externalApi.getInfoForCity("Leiria")).thenReturn(response);
        Mockito.when(cache.get("Leiria")).thenReturn(null);


        String city="Leiria";
        String parameter1="o3";
        String parameter2="co2";

        service.getInfoForCityAndSave(city);
        verifyExternalAPIisCalledOnce(city);
        verifyFindByCityAndParameterIsCalledOnce(city,parameter1);




        // the cache have the info, so the external API won't be called
        Mockito.when(cache.get("Leiria")).thenReturn(response);


        service.getInfoForCityAndSave(city);
        verifyExternalAPIisNotCalled(city);
        verifyFindByCityAndParameterIsCalledOnce(city,parameter2);



    }





    private void verifyFindByCityIsCalledOnce(String city) {
        Mockito.verify(resultsRepository, VerificationModeFactory.times(1)).findByCity(city);
        Mockito.reset(resultsRepository);
    }

    private void verifyFindByCityAndParameterIsCalledOnce(String city,String parameter) {
        Mockito.verify(resultsRepository, VerificationModeFactory.times(1)).findByCityAndParameter(city,parameter);
        Mockito.reset(resultsRepository);
    }



    private void verifyExternalAPIisCalledOnce(String city) throws Exception {
        Mockito.verify(externalApi, VerificationModeFactory.times(1)).getInfoForCity(city);
        Mockito.reset(externalApi);
    }

    private void verifyExternalAPIisNotCalled(String city) throws Exception {
        Mockito.verify(externalApi, VerificationModeFactory.times(0)).getInfoForCity(city);
        Mockito.reset(externalApi);
    }


    private void verifyFindAllResultsIsCalledOnce() {
        Mockito.verify(resultsRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(resultsRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(cacheStatsRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(resultsRepository);
    }

    private void verifyFindAllCacheStatsIsCalledOnce() {
        Mockito.verify(cacheStatsRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(cacheStatsRepository);
    }


}
