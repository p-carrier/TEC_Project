FROM maven
WORKDIR /app
COPY ./ /app/

RUN mvn clean package

ENTRYPOINT ["java", "-jar", "target/TEC_Project-1.0-SNAPSHOT-jar-with-dependencies.jar"]
