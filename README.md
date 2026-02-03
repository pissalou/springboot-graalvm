# springboot-graalvm
Demo SpringBoot webapp build to distroless image using GraalVM

## Getting started 
```bash
javac --version
native-image --version
CLASSNAME="HelloWorld"
echo "public class $CLASSNAME { public static void main(String[] args) { System.out.println(\"Hello, Native World! \"); } }" > $CLASSNAME.java
javac $CLASSNAME.java  # Compile to bytecode
native-image $CLASSNAME  # Generate binary
./${CLASSNAME,,}  # Execute binary
```
