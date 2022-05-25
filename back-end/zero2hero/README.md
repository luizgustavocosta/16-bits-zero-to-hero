## How to run

### Locally

The commands below will start the application

```bash
chmod +x gradlew && ./gradle bootRun
```

After the application has been started the following address will be available
1. Swagger - http://localhost:8080/swagger-ui/index.html
   1. User/Pass - tony/stark or boba/fett 
2. H2 - http://localhost:8080/h2-console/login.jsp?jsessionid=75e2f51f110a1827420f3a5d1a3df1fa
   1. URL connection - Same of application.properties file, `jdbc:h2:mem:moviesdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
   

### Docker


### Build project
```bash
chmod +x gradlew && ./gradlew clean build
```

After have the artifact, let's tell to Docker create our image

### Creating the image
```shell
docker build -t 16bits/zero2hero-be:0.0.1 .
```
or 
```shell
docker build --build-arg JAR_FILE=build/libs/\zero2hero-0.0.1-SNAPSHOT.jar -t 16bits/zero2hero-be:0.0.1 .
```

### Run the image

The value `88699e939bef` is the image id. 

Check using the command `docker image ls | grep 16bits`

```shell
docker run --name 16bits-hero -p8080:8080 -d 88699e939bef
```

### Publish the image
```shell
docker push 16bits/zero2hero-be:0.0.1
```

### References

- [openJDK image](https://hub.docker.com/layers/adoptopenjdk/library/adoptopenjdk/11-jre-hotspot-focal/images/sha256-eac1c6cff5fded2dd35fc94bb23e7862a08277bd71f9b352a99df5bc740459c3?context=explore)
- [Password encryption](https://bcrypt-generator.com)
- [Online Bcrypt](https://www.javainuse.com/onlineBcrypt)
- [LDAP file](https://www.digitalocean.com/community/tutorials/how-to-use-ldif-files-to-make-changes-to-an-openldap-system)
- [Fix wrong column](https://vladmihalcea.com/how-to-fix-wrong-column-type-encountered-schema-validation-errors-with-jpa-and-hibernate/)
- [Database model](http://www.databaseanswers.org/data_models/imdb/index.htm)
- [Best way to map one to many](https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/)
- [Swagger Spring Boot issue](https://www.youtube.com/watch?v=PpvnlaEHz2o&ab_channel=FromDeveloperToDeveloper)
- [Jacoco](https://reflectoring.io/jacoco/)
- [Sonar](https://github.com/SonarSource/sonarcloud-github-action-samples/tree/gradle)
- [CockroachDB](https://hub.docker.com/r/cockroachdb/cockroach/tags)