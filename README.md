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

This route is handled by Spring MVC and rendered with a Mustache template.

## Run tests

```bash
mvn test
```

## Build native image (GraalVM)

```bash
mvn -Pnative -DskipTests package
```

The native executable is generated under `target/`.

Choose libc target explicitly:

```bash
# glibc (default)
mvn -Pnative,native-glibc -DskipTests package

# musl
mvn -Pnative,native-musl -DskipTests package

# equivalent one-off override
mvn -Pnative -Dnative.libc=musl -DskipTests package
```

Note: musl builds require a musl-capable GraalVM toolchain in the build environment.

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

TODO:
- customize the devcontainer bash prompt to look like ohmyzsh
- stop hardcoding the port number
- introduce user login with OAuth spring security
- make gdb debugger available in devcontainer
- make a tf template to run on minikube with observability stack
- try `native-image` options to reduce image size (e.g. `--no-fallback`, `--no-server`, ...)
- set boundaries for the native image (e.g. `-H:MaxHeapSize=512m`).
- try musl-based images (e.g. `gcr.io/distroless/cc-debian13:musl`) to reduce image size.