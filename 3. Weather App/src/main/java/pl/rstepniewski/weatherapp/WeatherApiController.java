package pl.rstepniewski.weatherapp;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.rstepniewski.weatherapp.model.GeocodeResponse;

import java.util.NoSuchElementException;

//@RestController
//@RequestMapping("/weather")
public class WeatherApiController {

/*    @GetMapping("")
    public ResponseEntity<String> getWeatherForCity(@RequestParam String city) {
        //https://geocode.search.hereapi.com/v1/geocode?q=Warszawa&apiKey=DkHVGUkIPl-DuPMo_vgiOVP9kze6bIj5snAbAko2V8w
        String apiKey = "DkHVGUkIPl-DuPMo_vgiOVP9kze6bIj5snAbAko2V8w";
        String baseUrl = "https://geocode.search.hereapi.com/v1/geocode";
        String url = baseUrl + "?q=" + city + "&apiKey=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<GeocodeResponse> response = restTemplate.getForEntity(url, GeocodeResponse.class);
        final String cityResponse = response.getBody().getItems().stream()
                .filter(p -> p.getAddress().getCity().equals(city))
                .findFirst()
                .map(p -> p.getAddress().getCity())
                .orElseThrow(NoSuchElementException::new);

        return ResponseEntity.ok(cityResponse);
    }*/
}
