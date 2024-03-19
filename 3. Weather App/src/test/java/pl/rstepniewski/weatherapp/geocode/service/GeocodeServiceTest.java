package pl.rstepniewski.weatherapp.geocode.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import pl.rstepniewski.weatherapp.geocode.model.GeoAddress;
import pl.rstepniewski.weatherapp.geocode.model.GeoPosition;
import pl.rstepniewski.weatherapp.geocode.model.dto.GeocodeResponseDto;
import pl.rstepniewski.weatherapp.geocode.model.dto.ItemDto;

import java.util.List;

@RestClientTest(GeocodeService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class GeocodeServiceTest {

    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${Geocode.apiKey}")
    private String apiKey;

    @Test
    public void getCityGeoCode() throws JsonProcessingException {

        final GeoAddress geoAddress = new GeoAddress("Kielce, Woj. Świętokrzyskie, Polska",
                "POL",
                "Polska",
                "Woj. Świętokrzyskie",
                "Kielce",
                "Kielce",
                "25-525" );
        final GeoPosition geoPosition = new GeoPosition(50.87618, 20.62686);
        final GeocodeResponseDto geocodeMock = new GeocodeResponseDto(List.of(new ItemDto("Kielce, Woj. Świętokrzyskie, Polska", geoAddress, geoPosition)));

        this.mockServer
                .expect(MockRestRequestMatchers
                    .requestTo("https://geocode.search.hereapi.com/v1/geocode?q=Kielce&apiKey="+ apiKey))
                .andRespond(MockRestResponseCreators.withSuccess(objectMapper.writeValueAsString(geocodeMock), MediaType.APPLICATION_JSON));

        GeocodeResponseDto geocodeResponse = geocodeService.getCityGeoCode("Kielce");

        Assertions.assertEquals("Kielce", geocodeResponse.getItems().get(0).getAddress().getCity());
    }

}