FROM golang as gauge
RUN go get github.com/getgauge/gauge
FROM openjdk:13-jdk
COPY --from=gauge /go/bin/gauge /usr/local/bin/gauge
WORKDIR /gauge
ENTRYPOINT ["./gradlew", "clean"]