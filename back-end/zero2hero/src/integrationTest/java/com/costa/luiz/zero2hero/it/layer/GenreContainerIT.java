package com.costa.luiz.zero2hero.it.layer;

import com.costa.luiz.zero2hero.business.service.dto.GenreDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Genre - Testcontainers")
class GenreContainerIT extends Zero2HeroInfraSupport {

    @Container
    private static final GenericContainer backendContainer = new GenericContainer(
            DockerImageName.parse(APPLICATION_CONFIGURATION.getAppDockerName()))
            .dependsOn(COCKROACH_CONTAINER)
            .withNetwork(COCKROACH_CONTAINER.getNetwork())
            .withEnv("spring.profiles.active", APPLICATION_CONFIGURATION.getCockroachProfile())
            .withEnv("spring.datasource.url", buildJDBCConnection())
            .withEnv("server.port", Integer.toString(APPLICATION_CONFIGURATION.getAppSecondPort()))
            .withExposedPorts(APPLICATION_CONFIGURATION.getAppSecondPort())
            .waitingFor(Wait.forLogMessage(".*Started Zero2heroApplication.*\\n", 1));

    private static String buildJDBCConnection() {
        return "jdbc:postgresql://"
                + COCKROACH_CONTAINER.getNetworkAliases().iterator().next()
                + ":26257/postgres?sslmode=disable&user=root";
    }

    protected String url() {
        return "http://localhost:" + backendContainer.getMappedPort(APPLICATION_CONFIGURATION.getAppSecondPort());
    }

    @ParameterizedTest(name = "Calling the api for the user {0}")
    @MethodSource("getUsers")
    void getAllGenres(String user, String password) {
        var response = new RestTemplate()
                .exchange(UriComponentsBuilder.fromHttpUrl(url())
                                .path(APPLICATION_CONFIGURATION.getGenresApi())
                                .toUriString(),
                        HttpMethod.GET, new HttpEntity<>(
                                createHeaders(user, password)),
                        new ParameterizedTypeReference<List<GenreDto>>() {});
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(15, Objects.requireNonNull(response.getBody()).size());
    }

    static Stream<Arguments> getUsers() {
        return Stream.of(
                arguments(APPLICATION_CONFIGURATION.getAdminUser(), APPLICATION_CONFIGURATION.getAdminPassword()),
                arguments(APPLICATION_CONFIGURATION.getRegularUser(), APPLICATION_CONFIGURATION.getRegularUserPassword())
        );
    }
}
