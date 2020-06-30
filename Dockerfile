FROM openjdk:8-jre-alpine
COPY ./build/distributions/prometheus-java-example /usr/src/myapp
WORKDIR /usr/src/myapp
RUN chmod +x ./bin/prometheus-java-example
CMD ["./bin/prometheus-java-example"]
