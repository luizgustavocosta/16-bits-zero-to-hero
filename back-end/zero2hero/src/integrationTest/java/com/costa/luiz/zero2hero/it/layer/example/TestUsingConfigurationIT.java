package com.costa.luiz.zero2hero.it.layer.example;

import com.costa.luiz.zero2hero.business.service.dto.MovieDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DisplayName("Testing by configuration")
class TestUsingConfigurationIT {

    private static final ConfigurationReader CONFIGURATION_READER = ConfigurationReader.getInstance();

    @Container
    private static final GenericContainer backendContainer = new GenericContainer(
            DockerImageName.parse(CONFIGURATION_READER.getAppDockerName()))
            .withEnv("spring.profiles.active", CONFIGURATION_READER.getInMemoryProfile())
            .withEnv("server.port", String.valueOf(CONFIGURATION_READER.getAppSecondPort()))
            .withExposedPorts(CONFIGURATION_READER.getAppSecondPort())
            .waitingFor(Wait.forLogMessage(".*Started Zero2heroApplication.*\\n", 1));

    @DisplayName("Find all movies - in memory profile")
    @Test
    void shouldFindAllMovies() {
        var response = new RestTemplate()
                .exchange(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:" +
                                        backendContainer.getMappedPort(CONFIGURATION_READER.getAppSecondPort()))
                                .path(CONFIGURATION_READER.getMoviesApi())
                                .toUriString(),
                        HttpMethod.GET, new HttpEntity<>(createHeaders()),
                        MovieDto[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(11, Objects.requireNonNull(response.getBody()).length);
        assertEquals("The Lord of the rings", Arrays.stream(response.getBody()).iterator().next().getName());
    }

    private HttpHeaders createHeaders() {
        return new HttpHeaders() {{
            var auth = CONFIGURATION_READER.getAdminUser() + ":" + CONFIGURATION_READER.getAdminPassword();
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            set("Authorization", "Basic " + new String(encodedAuth));
        }};
    }

}
