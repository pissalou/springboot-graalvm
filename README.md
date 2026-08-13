# springboot-graalvm

Maven project for a Spring Boot 3 web backend.

## Requirements

- Java 21
- Maven 3.9+

## Run locally

```bash
mvn spring-boot:run
```

Then open:

- `http://localhost:8080/hello`

## Run tests

```bash
mvn test
```

## Build native image (GraalVM)

```bash
mvn -Pnative -DskipTests package
```

The native executable is generated under `target/`.


## Build distroless container

```bash
podman --version
BINARYNAME="springboot-graalvm"
podman build . -t $BINARYNAME:latest -f- << EOF
FROM gcr.io/distroless/cc-debian13
COPY target/$BINARYNAME .
ENTRYPOINT ["/$BINARYNAME"]
EOF
podman run -p 8080:8080 $BINARYNAME 2>/dev/null
```

