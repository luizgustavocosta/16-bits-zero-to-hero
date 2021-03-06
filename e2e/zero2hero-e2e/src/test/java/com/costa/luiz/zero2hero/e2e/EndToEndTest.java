package com.costa.luiz.zero2hero.e2e;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.CockroachContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Testcontainers
@DisplayName("16 Bits - Movie E2E")
class EndToEndTest {
    private RemoteWebDriver driver;

    private static final ApplicationConfigurationReader APPLICATION_CONFIGURATION_READER
            = ApplicationConfigurationReader.getInstance();

    @BeforeEach
    void setUp() {
        driver = chrome.getWebDriver();
    }

    @Container
    private static final CockroachContainer COCKROACH_CONTAINER = new CockroachContainer(
            DockerImageName.parse(APPLICATION_CONFIGURATION_READER.getDockerDatabaseName()))
            .withNetwork(Network.SHARED)
            .withCommand("start-single-node --insecure")
            .withNetworkAliases("cockroachDB")
            .withExposedPorts(APPLICATION_CONFIGURATION_READER.getAppCockroachPortUI(),
                    APPLICATION_CONFIGURATION_READER.getAppCockroachPortJDBC());

    @Container
    private static final GenericContainer backendContainer = new GenericContainer(
            DockerImageName.parse(APPLICATION_CONFIGURATION_READER.getDockerBackendName()))
            .dependsOn(COCKROACH_CONTAINER)
            .withNetwork(COCKROACH_CONTAINER.getNetwork())
            .withEnv("spring.profiles.active", APPLICATION_CONFIGURATION_READER.getCockroachProfile())
            .withEnv("spring.datasource.url", buildJDBCConnection())
            .withEnv("server.port", APPLICATION_CONFIGURATION_READER.getBackendPort())
            .withExposedPorts(Integer.parseInt(APPLICATION_CONFIGURATION_READER.getBackendPort()))
            .withNetworkAliases("backend")
            .waitingFor(Wait.forLogMessage(".*Started Zero2heroApplication.*\\n", 1));

    @Container
    private static final GenericContainer frontEndContainer = new GenericContainer(
            DockerImageName.parse(APPLICATION_CONFIGURATION_READER.getDockerFrontendName()))
            .withExposedPorts(APPLICATION_CONFIGURATION_READER.getFrontendPort())
            .withEnv("REACT_APP_BACKEND_SERVER_URL", "http://" +
                    backendContainer.getNetworkAliases().iterator().next() +
                    ":" + APPLICATION_CONFIGURATION_READER.getBackendPort())
            .withNetwork(backendContainer.getNetwork())
            .withNetworkAliases("frontend")
            .dependsOn(backendContainer)
            .waitingFor(Wait.forLogMessage(".*compiled successfully in.*\\n", 1));


    @Container
    private static final BrowserWebDriverContainer chrome = (BrowserWebDriverContainer) new BrowserWebDriverContainer()
            .withCapabilities(new ChromeOptions())
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL, new File("build"))
            .dependsOn(frontEndContainer)
            .withNetworkAliases("chrome")
            .withNetwork(frontEndContainer.getNetwork());

    private static String buildJDBCConnection() {
        return "jdbc:postgresql://"
                + COCKROACH_CONTAINER.getNetworkAliases().iterator().next()
                + ":" +
                APPLICATION_CONFIGURATION_READER.getAppCockroachPortJDBC()
                + "/postgres?sslmode=disable&user=root";
    }

    @ParameterizedTest(name = "The user {0} should see the Movie list page? Answer: {2} ")
    @MethodSource("getUsersForLogin")
    @DisplayName("Login with valid credentials")
    void loginWithValidCredentials(String user, String pass, boolean expected) {

        driver.get("http://" + frontEndContainer.getNetworkAliases().iterator().next() +
                ":" + APPLICATION_CONFIGURATION_READER.getFrontendPort());

        var username = driver.findElement(By.id("username"));
        var password = driver.findElement(By.id("password"));

        username.sendKeys(user);
        password.sendKeys(pass);

        var signButton = driver.findElement(By.id("signIn"));
        signButton.click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        var movieListPage = driver.findElementsById("movieList").size() == 1;

        assertEquals(expected, movieListPage);
    }

    @ParameterizedTest(name = "The user {0} should be able to create a new movie? Answer: {2} ")
    @MethodSource("getUsersToCreateANewMovie")
    @DisplayName("Authorization to create a new movie")
    void authorizationToCreateANewMovie(String user, String pass, boolean expected) {
        driver.get("http://" + frontEndContainer.getNetworkAliases().iterator().next() +
                ":" + APPLICATION_CONFIGURATION_READER.getFrontendPort());
        var username = driver.findElement(By.id("username"));
        var password = driver.findElement(By.id("password"));

        username.sendKeys(user);
        password.sendKeys(pass);

        var signButton = driver.findElement(By.id("signIn"));
        signButton.click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        var addButton = driver.findElementsById("addButton").size() == 1;

        assertEquals(expected, addButton);
    }

    static Stream<Arguments> getUsersForLogin() {
        boolean expectedForUsers = true;
        boolean expectedForNonUsers = false;

        return Stream.of(
                arguments(APPLICATION_CONFIGURATION_READER.getAdminUser().get(0), // boba
                        APPLICATION_CONFIGURATION_READER.getAdminPassword().get(0), //fett
                        expectedForUsers),

                arguments(APPLICATION_CONFIGURATION_READER.getRegularUser(),// james
                        APPLICATION_CONFIGURATION_READER.getRegularUserPassword(), // bond
                        expectedForUsers),

                arguments(APPLICATION_CONFIGURATION_READER.getNonUser(), // darth
                        APPLICATION_CONFIGURATION_READER.getNonUserPassword(), //vader
                        expectedForNonUsers)
        );
    }

    static Stream<Arguments> getUsersToCreateANewMovie() {
        boolean expectedForAdminUsers = true;
        boolean expectedForRegularUsers = false;
        return Stream.of(
                arguments(APPLICATION_CONFIGURATION_READER.getAdminUser().get(0), // boba
                        APPLICATION_CONFIGURATION_READER.getAdminPassword().get(0), //fett
                        expectedForAdminUsers),

                arguments(APPLICATION_CONFIGURATION_READER.getAdminUser().get(1), // tony
                        APPLICATION_CONFIGURATION_READER.getAdminPassword().get(1), //stark
                        expectedForAdminUsers),

                arguments(APPLICATION_CONFIGURATION_READER.getRegularUser(),// james
                        APPLICATION_CONFIGURATION_READER.getRegularUserPassword(), // bond
                        expectedForRegularUsers)
        );
    }
}
