package com.costa.luiz.zero2hero.it.layer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Configuration {

    Properties properties = new Properties();

    public Configuration() {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {

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

    public String getAppDockerName() {
        return properties.getProperty("app.docker.name");
    }

    public int getAppPort() {
        return Integer.parseInt(properties.getProperty("app.port"));
    }

    public String getGenreApi() {
        return properties.getProperty("app.genre.api");
    }
}
