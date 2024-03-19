package pl.rstepniewski.weatherapp.geocode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.rstepniewski.weatherapp.geocode.model.dto.GeocodeResponseDto;
import pl.rstepniewski.weatherapp.openmeteo.exception.ApiOpenMeteoException;

@Service
@RequiredArgsConstructor
public class GeocodeService {
    private final RestClient restClient;

    @Value("${Geocode.apiKey}")
    private String apiKey;

    public GeocodeService() {
        this.restClient = RestClient.create();
    }

    @Retryable(value = {HttpClientErrorException.class, HttpServerErrorException.class, HttpStatusCodeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    @Cacheable(value = "cityGeoCodes", key = "#cityName")
    public GeocodeResponseDto getCityGeoCode(final String cityName) {

        final String url = buildUrl(cityName);

        final GeocodeResponseDto geocodeResponse = this.restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(GeocodeResponseDto.class);

        if (geocodeResponse == null) {
            throw new ApiOpenMeteoException("Received null response from geocode service");
        }

        return geocodeResponse;
    }

    private String buildUrl(final String cityName) {
        final String baseUrl = "https://geocode.search.hereapi.com/v1/geocode";

        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("q", cityName)
                .queryParam("apiKey", apiKey);
        return uriBuilder.toUriString();
    }
}
