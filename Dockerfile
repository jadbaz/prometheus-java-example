FROM openjdk:8-jre-alpine
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN chmod +x gradlew
RUN ./gradlew build
CMD ["./gradlew", "run"]
