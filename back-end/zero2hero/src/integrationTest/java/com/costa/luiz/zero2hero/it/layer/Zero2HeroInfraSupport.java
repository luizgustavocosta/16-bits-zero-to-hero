package com.costa.luiz.zero2hero.it.layer;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.testcontainers.containers.CockroachContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;

@Testcontainers
abstract class Zero2HeroInfraSupport {

    protected static final ApplicationConfiguration APPLICATION_CONFIGURATION = ApplicationConfiguration.getInstance();

    @Container
    static CockroachContainer COCKROACH_CONTAINER = new CockroachContainer(
            DockerImageName.parse(APPLICATION_CONFIGURATION.getDatabaseDockerName()))
            .withNetwork(Network.SHARED)
            .withReuse(true) // TODO - Future
            .withCommand("start-single-node --insecure")
            .withExposedPorts(APPLICATION_CONFIGURATION.getAppCockroachPortUI(),
                    APPLICATION_CONFIGURATION.getAppCockroachPortJDCB());

    protected HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            var auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            set("Authorization", "Basic " + new String(encodedAuth));
        }};
    }
}
