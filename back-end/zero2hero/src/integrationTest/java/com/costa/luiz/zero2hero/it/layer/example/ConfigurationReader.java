package com.costa.luiz.zero2hero.it.layer.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.nonNull;

class ConfigurationReader {

    private Properties properties;

    private static ConfigurationReader instance = null;

    public static ConfigurationReader getInstance() {
        if (nonNull(instance)) {
            return instance;
        }
        instance = new ConfigurationReader();
        return instance;
    }

    private ConfigurationReader() {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("application-example.properties")) {
            InputStream file = Optional.ofNullable(inputStream).orElseThrow(IllegalStateException::new);
            properties = new Properties();
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

    public String getInMemoryProfile() {
        return properties.getProperty("app.profiles.in-memory");
    }

    public String getAppDockerName() {
        return properties.getProperty("app.docker.name");
    }

    public int getAppSecondPort() {
        return Integer.parseInt(properties.getProperty("app.second.port"));
    }

    public String getMoviesApi() {
        return properties.getProperty("app.movies.api");
    }

}
