# springboot-graalvm
Demo SpringBoot webapp build to distroless image using GraalVM

## Getting started
Build binary:
```bash
javac --version
native-image --version
CLASSNAME="HelloWorld"
echo "public class $CLASSNAME { public static void main(String[] args) { System.out.println(\"Hello, Native World! \"); } }" > $CLASSNAME.java
javac $CLASSNAME.java  # Compile to bytecode
native-image $CLASSNAME  # Generate binary
./${CLASSNAME,,}  # Execute binary
ldd ${CLASSNAME,,}
```

Build distroless container:
```bash
podman --version
BINARYNAME="helloworld"
podman build . -t $BINARYNAME:latest -f- << EOF
FROM gcr.io/distroless/cc-debian13
COPY $BINARYNAME .
ENTRYPOINT ["/$BINARYNAME"]
EOF
podman run $BINARYNAME 2>/dev/null
```

