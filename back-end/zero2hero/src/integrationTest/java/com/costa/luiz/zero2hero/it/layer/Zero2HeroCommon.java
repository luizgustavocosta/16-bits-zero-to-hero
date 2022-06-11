package com.costa.luiz.zero2hero.it.layer;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;

@Testcontainers
public class Zero2HeroCommon {

    protected static final Configuration CONFIGURATION = new Configuration();

    @Container
    GenericContainer MOVIE_APP = new GenericContainer(DockerImageName.parse(CONFIGURATION.getAppDockerName()))
            .withEnv("spring.profiles.active", CONFIGURATION.getInMemoryProfile())
            .withExposedPorts(CONFIGURATION.getAppPort());

    protected String url() {
        return "http://localhost:" + MOVIE_APP.getMappedPort(CONFIGURATION.getAppPort());
    }

    protected HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}
