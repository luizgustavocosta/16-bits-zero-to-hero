package com.costa.luiz.zero2hero.it.layer;

import com.costa.luiz.zero2hero.business.service.dto.GenreDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GenreIT extends Zero2HeroCommon {

    @ParameterizedTest(name = "Calling the api for the user {0}")
    @MethodSource("getUsers")
    void getAllGenres(String user, String password) {
        var response = new RestTemplate()
                .exchange(UriComponentsBuilder.fromHttpUrl(url())
                                .path(CONFIGURATION.getGenreApi())
                                .toUriString(),
                        HttpMethod.GET, new HttpEntity<>(
                                createHeaders(user, password)),
                        GenreDto[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    static Stream<Arguments> getUsers() {
        return Stream.of(
                arguments(CONFIGURATION.getAdminUser(), CONFIGURATION.getAdminPassword()),
                arguments(CONFIGURATION.getRegularUser(), CONFIGURATION.getRegularUserPassword())
        );
    }
}
