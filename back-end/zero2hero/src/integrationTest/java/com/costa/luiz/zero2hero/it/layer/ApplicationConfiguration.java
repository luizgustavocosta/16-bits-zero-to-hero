package com.costa.luiz.zero2hero.it.layer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.nonNull;

public class ApplicationConfiguration {

    Properties properties = new Properties();

    private static ApplicationConfiguration instance = null;

    public static ApplicationConfiguration getInstance() {
        if (nonNull(instance)) {
            return instance;
        }
        instance = new ApplicationConfiguration();
        return instance;
    }

    private ApplicationConfiguration() {
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

    public String getGenreApi() {
        return properties.getProperty("app.genre.api");
    }

    public int getAppCockroachPortUI() {
        return Integer.parseInt(properties.getProperty("app.cockroach.port.ui"));
    }

    public int getAppCockroachPortJDCB() {
        return Integer.parseInt(properties.getProperty("app.cockroach.port.jdbc"));
    }
}
