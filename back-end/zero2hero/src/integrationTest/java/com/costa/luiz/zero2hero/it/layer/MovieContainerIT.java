package com.costa.luiz.zero2hero.it.layer;

import com.costa.luiz.zero2hero.business.service.dto.MovieDto;
import com.costa.luiz.zero2hero.persistence.repository.movie.Classification;
import com.costa.luiz.zero2hero.persistence.repository.movie.Genre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Movies - Testcontainers")
class MovieContainerIT extends Zero2HeroInfraSupport {

    private final RestTemplate restTemplate = new RestTemplate();

    @Container
    private final GenericContainer backendContainer = new GenericContainer(
            DockerImageName.parse(APPLICATION_CONFIGURATION.getAppDockerName()))
            .dependsOn(COCKROACH_CONTAINER)
            .withNetwork(COCKROACH_CONTAINER.getNetwork())
            .withEnv("spring.profiles.active", APPLICATION_CONFIGURATION.getCockroachProfile())
            .withEnv("spring.datasource.url", buildJDBCConnection())
            .withEnv("server.port", Integer.toString(APPLICATION_CONFIGURATION.getAppSecondPort()))
            .withExposedPorts(APPLICATION_CONFIGURATION.getAppSecondPort())
            .waitingFor(Wait.forLogMessage(".*Started Zero2heroApplication.*\\n", 1));

    private String buildJDBCConnection() {
        return "jdbc:postgresql://"
                + COCKROACH_CONTAINER.getNetworkAliases().iterator().next()
                + ":26257/postgres?sslmode=disable&user=root";
    }

    protected String url() {
        return "http://localhost:" + backendContainer.getMappedPort(APPLICATION_CONFIGURATION.getAppSecondPort());
    }

    @ParameterizedTest(name = "Calling the api for the user {0}")
    @MethodSource("getUsers")
    void getAllMovies(String user, String password) {
        var response = restTemplate
                .exchange(UriComponentsBuilder.fromHttpUrl(url())
                                .path(APPLICATION_CONFIGURATION.getGenresApi())
                                .toUriString(),
                        HttpMethod.GET, new HttpEntity<>(
                                createHeaders(user, password)),
                        MovieDto[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        List<MovieDto> movieResponse = Arrays.asList(Objects.requireNonNull(response.getBody()));

        assertAll("Assert the movies",
                () -> assertThat(movieResponse.size())
                        .as("Movies expected").isEqualTo(15),
                () -> assertThat(movieResponse)
                        .filteredOn("name", "Carandiru").isNotNull(),
                () -> assertThat(movieResponse)
                        .filteredOn("country", "Japan").isEmpty()
        );
    }

    @Test
    void deleteAMovie() {
        var beforeDelete = getAllMovies();
        var movieId = "1";
        ResponseEntity<String> response = restTemplate
                .exchange(UriComponentsBuilder.fromHttpUrl(url())
                                .path(APPLICATION_CONFIGURATION.getMoviesApi())
                                .pathSegment(movieId)
                                .toUriString(),
                        HttpMethod.DELETE,
                        new HttpEntity<>(createHeaders(
                                APPLICATION_CONFIGURATION.getAdminUser(),
                                APPLICATION_CONFIGURATION.getAdminPassword())),
                        String.class);
        var afterDelete = getAllMovies();

        assertNotNull(response.getStatusCode().is2xxSuccessful());
        assertNotEquals(beforeDelete.size(), afterDelete.size());
    }

    @Test
    void createANewMovie() {
        var beforeInsert = getAllMovies();
        var newMovie = MovieDto.builder()
                .id(5L)
                .name("Top Gun: Maverick")
                .rating(10d)
                .language("English")
                .country("USA")
                .classification(Classification.PG13)
                .duration(131)
                .reviews(Collections.emptyList())
                .year(2022)
                .lastUpdate(LocalDateTime.now())
                .genreIds(Collections.singletonList(2L))
                .build();

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(newMovie);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        var headers = createHeaders(
                APPLICATION_CONFIGURATION.getAdminUser(),
                APPLICATION_CONFIGURATION.getAdminPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);


        var request = new HttpEntity<>(json, headers);

        try {
            ResponseEntity<MovieDto> response = restTemplate
                    .exchange(UriComponentsBuilder.fromHttpUrl(url())
                                    .path(APPLICATION_CONFIGURATION.getMoviesApi())
                                    .toUriString(),
                            HttpMethod.PUT,
                            request,
                            MovieDto.class);

            System.out.println(response);

            var afterInsert = getAllMovies();

            assertNotNull(response.getStatusCode().is2xxSuccessful());
            assertNotEquals(beforeInsert.size(), afterInsert.size());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private List<MovieDto> getAllMovies() {
        return Stream.of(restTemplate
                .exchange(UriComponentsBuilder.fromHttpUrl(url())
                                .path(APPLICATION_CONFIGURATION.getMoviesApi())
                                .toUriString(),
                        HttpMethod.GET, new HttpEntity<>(
                                createHeaders(APPLICATION_CONFIGURATION.getAdminUser(),
                                        APPLICATION_CONFIGURATION.getAdminPassword())),
                        MovieDto[].class).getBody()).collect(Collectors.toUnmodifiableList());
    }

    static Stream<Arguments> getUsers() {
        return Stream.of(
                arguments(APPLICATION_CONFIGURATION.getAdminUser(), APPLICATION_CONFIGURATION.getAdminPassword()),
                arguments(APPLICATION_CONFIGURATION.getRegularUser(), APPLICATION_CONFIGURATION.getRegularUserPassword())
        );
    }
}
