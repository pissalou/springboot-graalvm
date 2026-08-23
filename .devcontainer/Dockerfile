FROM ghcr.io/graalvm/native-image-community:21

#RUN printf "[main]\nexcludepkgs=java-21-openjdk*\n" > /etc/dnf/dnf.conf
#RUN microdnf install -y --setopt=install_weak_deps=0 git maven-openjdk21 && microdnf clean all
#RUN microdnf install -y podman && microdnf clean all

# or even more lightweight:
RUN curl -sL https://dlcdn.apache.org/maven/maven-3/3.9.16/binaries/apache-maven-3.9.16-bin.tar.gz | tar -xzf - -C /opt && ln -s /opt/apache-maven-3.9.16/bin/mvn /usr/bin/mvn

# libicu is required by CLion's .NET-based backend components (e.g. ReSharper host); without it you get "Couldn't find a valid ICU package installed".
RUN microdnf install -y libicu && microdnf clean all

RUN microdnf install -y shadow-utils && microdnf clean all

RUN groupadd --gid 1000 dev \
	&& useradd --uid 1000 --gid 1000 --create-home --shell /bin/bash dev

USER dev
