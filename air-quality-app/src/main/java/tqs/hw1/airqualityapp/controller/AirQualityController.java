package tqs.hw1.airqualityapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.hw1.airqualityapp.models.CacheStats;
import tqs.hw1.airqualityapp.models.Results;
import tqs.hw1.airqualityapp.external.AirQualityException;
import tqs.hw1.airqualityapp.services.AirQualityService;
import java.util.List;


@Controller
@RequestMapping("/tqs")
public class AirQualityController {

    @Autowired
    private AirQualityService service;



    // REST API FOR WEB PAGES



    @GetMapping(value="/get/{city}")
    @ResponseBody
    public List<Results> getAirQualityCityInfo(@PathVariable("city") String city) throws AirQualityException {

        service.getInfoForCityAndSave(city);

        return service.findAllResults();

    }


    @GetMapping(value="/airQualityPT")
    public String getAirQualityPage()
    {

        return "airQualityPT";

    }


    @GetMapping(value="/cacheStats")
    public String getCacheStatsPage(Model model) {

        CacheStats cacheStats=service.cacheStatsSave();

        model.addAttribute("requests", cacheStats.getNumberOfRequests());
        model.addAttribute("hits", cacheStats.getNumberOfHits());
        model.addAttribute("misses", cacheStats.getNumberOfMisses());

        return "cacheStats";

    }




    // REST API FOR TESTS, DOES THE SAME THAT getCacheStatsPage DO, but without model


    @GetMapping(value="/get/cacheStats")
    @ResponseBody
    public CacheStats getCacheStats() {

        return service.cacheStatsSave();

    }
}
