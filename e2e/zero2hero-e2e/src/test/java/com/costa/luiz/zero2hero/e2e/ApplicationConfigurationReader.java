package com.costa.luiz.zero2hero.e2e;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.nonNull;

public class ApplicationConfigurationReader {

    Properties properties = new Properties();

    private static ApplicationConfigurationReader instance = null;

    public static ApplicationConfigurationReader getInstance() {
        if (nonNull(instance)) {
            return instance;
        }
        instance = new ApplicationConfigurationReader();
        return instance;
    }

    private ApplicationConfigurationReader() {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("application-e2e.properties")) {
            InputStream file = Optional.ofNullable(inputStream).orElseThrow(IllegalStateException::new);
            properties.load(file);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public List<String> getAdminUser() {
        return
                Arrays.asList(properties.getProperty("app.admin.user").split(","));
    }

    public List<String> getAdminPassword() {
        return Arrays.asList(properties.getProperty("app.admin.password").split(","));
    }

    public String getRegularUser() {
        return properties.getProperty("app.regular.user");
    }

    public String getRegularUserPassword() {
        return properties.getProperty("app.regular.password");
    }

    public String getNonUser() {
        return properties.getProperty("app.nonuser.user");
    }

    public String getNonUserPassword() {
        return properties.getProperty("app.nonuser.password");
    }

    public String getInMemoryProfile() {
        return properties.getProperty("app.profiles.in-memory");
    }

    public String getCockroachProfile() {
        return properties.getProperty("app.profiles.cockroach");
    }

    public String getDockerBackendName() {
        return properties.getProperty("app.docker.backend.name");
    }

    public String getDockerDatabaseName() {
        return properties.getProperty("app.docker.database.name");
    }

    public int getAppCockroachPortUI() {
        return Integer.parseInt(properties.getProperty("app.cockroach.port.ui"));
    }

    public int getAppCockroachPortJDBC() {
        return Integer.parseInt(properties.getProperty("app.cockroach.port.jdbc"));
    }

    public String getBackendPort() {
        return properties.getProperty("app.backend.port");
    }

    public String getDockerFrontendName() {
        return properties.getProperty("app.docker.frontend.name");
    }

    public Integer getFrontendPort() {
        return Integer.parseInt(properties.getProperty("app.frontend.port"));
    }
}
