package com.costa.luiz.zero2hero.it.layer;

import java.io.IOException;
import java.io.InputStream;
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
                .getResourceAsStream("application-testcontainers.properties")) {
            InputStream file = Optional.ofNullable(inputStream).orElseThrow(IllegalStateException::new);
            properties.load(file);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public String getAdminUser() {
        return properties.getProperty("app.admin.user");
    }

    public String getAdminPassword() {
        return properties.getProperty("app.admin.password");
    }

    public String getRegularUser() {
        return properties.getProperty("app.regular.user");
    }

    public String getRegularUserPassword() {
        return properties.getProperty("app.regular.password");
    }

    public String getInMemoryProfile() {
        return properties.getProperty("app.profiles.in-memory");
    }

    public String getCockroachProfile() {
        return properties.getProperty("app.profiles.cockroach");
    }

    public String getAppDockerName() {
        return properties.getProperty("app.docker.name");
    }

    public String getDatabaseDockerName() {
        return properties.getProperty("app.docker.database.name");
    }

    public int getAppPort() {
        return Integer.parseInt(properties.getProperty("app.port"));
    }

    public int getAppSecondPort() {
        return Integer.parseInt(properties.getProperty("app.second.port"));
    }

    public String getGenresApi() {
        return properties.getProperty("app.genres.api");
    }

    public String getMoviesApi() {
        return properties.getProperty("app.movies.api");
    }

    public int getAppCockroachPortUI() {
        return Integer.parseInt(properties.getProperty("app.cockroach.port.ui"));
    }

    public int getAppCockroachPortJDCB() {
        return Integer.parseInt(properties.getProperty("app.cockroach.port.jdbc"));
    }
}
