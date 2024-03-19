package pl.rstepniewski.weatherapp.openmeteo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import pl.rstepniewski.weatherapp.geocode.model.GeoPosition;
import pl.rstepniewski.weatherapp.openmeteo.model.MeteoHourlyScore;
import pl.rstepniewski.weatherapp.openmeteo.model.MeteoHourlyUnits;
import pl.rstepniewski.weatherapp.openmeteo.model.dto.MeteoResponseDto;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.WeatherRequestFormDto;

@RestClientTest(OpenMeteoService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class OpenMeteoServiceTest {

    @Autowired
    private OpenMeteoService openMeteoService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getWeatherForecast() throws JsonProcessingException { //zmiana nazwy metody
        final MeteoResponseDto meteoResponseMock = new MeteoResponseDto(
                52.2356,
                21.01037,
                new MeteoHourlyUnits("iso8601", "°C", "mm", "km/h"),
                new MeteoHourlyScore(
                        new String[]{"2024-03-14T00:00", "2024-03-14T01:00", "2024-03-14T02:00"},
                        new double[]{1.0, 2.0, 3.0},
                        new double[]{1.0, 2.0, 3.0},
                        new double[]{1.0, 2.0, 3.0}
                )
        );

        final GeoPosition geoPosition = new GeoPosition(52.2356, 21.01037);
        final WeatherRequestFormDto weatherRequestForm = new WeatherRequestFormDto("Miasto", true, true, true);

        this.mockServer
                .expect(MockRestRequestMatchers
                        .requestTo("https://api.open-meteo.com/v1/forecast?latitude=52.2356&longitude=21.01037&hourly=temperature_2m,rain,wind_speed_10m")) //sprawdzić uri
                .andRespond(MockRestResponseCreators.withSuccess(objectMapper.writeValueAsString(meteoResponseMock), MediaType.APPLICATION_JSON));

        MeteoResponseDto meteoResponse = this.openMeteoService.getWeatherForecast(geoPosition, weatherRequestForm);

        Assertions.assertEquals(3, meteoResponse.getHourlyScore().getTemperature2m().length);
        Assertions.assertEquals(3, meteoResponse.getHourlyScore().getRain().length);
        Assertions.assertEquals(3, meteoResponse.getHourlyScore().getWindSpeed10m().length);
 /*       Assertions.assertNotEquals(null, meteoResponse.getHourlyScore().getRain());
        Assertions.assertNotEquals(null, meteoResponse.getHourlyScore().getWindSpeed10m());
        Assertions.assertNotEquals(null, meteoResponse.getHourlyScore().getTemperature2m());*/
    }
}