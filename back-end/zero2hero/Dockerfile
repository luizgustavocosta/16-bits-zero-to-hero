#FROM adoptopenjdk:11-jre-hotspot@sha256:201415101fea9f9aee9f6760330fdbf42685fac1ec467408c10e572fbb90fec7
FROM adoptopenjdk:11-jre-hotspot-focal@sha256:eac1c6cff5fded2dd35fc94bb23e7862a08277bd71f9b352a99df5bc740459c3
#RUN addgroup -S 16bits && adduser -S 16bits -G 16bits
#USER 16bits:16bits
ARG JAR_FILE=build/libs/zero2hero-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} zeroToHero.jar
ENTRYPOINT ["java","-jar","/zeroToHero.jar"]
EXPOSE 8080