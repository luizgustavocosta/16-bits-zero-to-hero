package com.costa.luiz.zero2hero.it.layer;

import com.costa.luiz.zero2hero.business.service.dto.MovieDto;
import com.costa.luiz.zero2hero.persistence.repository.movie.Classification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Movies - Testcontainers")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieContainerIT extends Zero2HeroInfraSupport {

    private final RestTemplate restTemplate = new RestTemplate();

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

    @ParameterizedTest(name = "Find all movies for the user {0}")
    @MethodSource("getUsers")
    @Order(1)
    void findAllMoviesBy(String user, String password) {
        var response = restTemplate
                .exchange(UriComponentsBuilder.fromHttpUrl(url())
                                .path(APPLICATION_CONFIGURATION.getMoviesApi()).toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(createHeaders(user, password)),
                        new ParameterizedTypeReference<List<MovieDto>>() {});

        var movieResponse = response.getBody();

        assertAll("Assert the movies",
                () -> assertThat(movieResponse.size())
                        .as("Movies expected").isEqualTo(11),
                () -> assertThat(movieResponse)
                        .filteredOn("name", "Carandiru").isNotNull(),
                () -> assertThat(movieResponse)
                        .filteredOn("country", "Japan").isEmpty()
        );
    }

    @Test
    @Order(2)
    void deleteAMovie() {
        var beforeDelete = getAllMovies();
        var movieId = "1";
        restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url())
                                .path(APPLICATION_CONFIGURATION.getMoviesApi())
                                .pathSegment(movieId)
                                .toUriString(),
                        HttpMethod.DELETE,
                        new HttpEntity<>(createHeaders(
                                APPLICATION_CONFIGURATION.getAdminUser(),
                                APPLICATION_CONFIGURATION.getAdminPassword())),
                        Void.class);

        var afterDelete = getAllMovies();

        assertThat(afterDelete).filteredOn("id", movieId).isNullOrEmpty();
        assertNotEquals(beforeDelete.size(), afterDelete.size());
    }

    @DisplayName("Create the movie Top Gun: Maverick")
    @Test
    @Order(3)
    void createANewMovie() {
        var beforeInsert = getAllMovies();

        var newMovie = MovieDto.builder()
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

        var headers = createHeaders(
                APPLICATION_CONFIGURATION.getAdminUser(),
                APPLICATION_CONFIGURATION.getAdminPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);

        var request = new HttpEntity<>(newMovie, headers);

        restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url())
                        .path(APPLICATION_CONFIGURATION.getMoviesApi()).toUriString(),
                        HttpMethod.PUT,
                        request,
                        MovieDto.class);

        var afterInsert = getAllMovies();

        assertNotEquals(beforeInsert.size(), afterInsert.size());
    }

    @DisplayName("Update the name of the movie The Good the Bad and the Ugly")
    @Test
    @Order(4)
    void updateAnExistingMovie() {
        MovieDto movieDto = getAllMovies().stream().filter(movie ->
                        movie.getName().equalsIgnoreCase("The Good the Bad and the Ugly"))
                .findFirst().orElseThrow(IllegalArgumentException::new);

        var expectedName = "O bom o mau e o feio";
        movieDto.setName(expectedName);

        var headers = createHeaders(
                APPLICATION_CONFIGURATION.getAdminUser(),
                APPLICATION_CONFIGURATION.getAdminPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);

        var request = new HttpEntity<>(movieDto, headers);

        restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl(url()).path(APPLICATION_CONFIGURATION.getMoviesApi()).toUriString(),
                HttpMethod.PUT,
                request,
                Void.class);

        var afterInsert = getAllMovies();

        assertThat(afterInsert).filteredOn("name", expectedName).isNotNull();

    }

    private List<MovieDto> getAllMovies() {
        return Stream.of(Objects.requireNonNull(restTemplate
                .exchange(UriComponentsBuilder.fromHttpUrl(url())
                                .path(APPLICATION_CONFIGURATION.getMoviesApi())
                                .toUriString(),
                        HttpMethod.GET, new HttpEntity<>(
                                createHeaders(APPLICATION_CONFIGURATION.getAdminUser(),
                                        APPLICATION_CONFIGURATION.getAdminPassword())),
                        MovieDto[].class).getBody())).collect(Collectors.toUnmodifiableList());
    }

    static Stream<Arguments> getUsers() {
        return Stream.of(
                arguments(APPLICATION_CONFIGURATION.getAdminUser(), APPLICATION_CONFIGURATION.getAdminPassword()),
                arguments(APPLICATION_CONFIGURATION.getRegularUser(), APPLICATION_CONFIGURATION.getRegularUserPassword())
        );
    }
}
