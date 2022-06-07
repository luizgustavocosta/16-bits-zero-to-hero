package com.costa.luiz.zero2hero.it.container;

import com.costa.luiz.zero2hero.business.service.dto.GenreDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;

@Testcontainers
class GenreContainerIT {

    @Container
    public static GenericContainer movie = new GenericContainer(DockerImageName.parse("16bits/zero2hero-be:0.0.2"))
            .withEnv("spring.profiles.active", "default,in-memory")
            .withExposedPorts(8080);

    @Test
    void helloMovie() {
        var response = new RestTemplate()
                .exchange("http://localhost:" + movie.getMappedPort(8080) + "/api/v1/genres",
                        HttpMethod.GET, new HttpEntity<>(createHeaders("tony", "stark")),
                        GenreDto[].class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response);
    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

}
