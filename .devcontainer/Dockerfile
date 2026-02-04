FROM ghcr.io/graalvm/native-image-community:21

RUN microdnf install -y git && microdnf clean all
RUN microdnf install -y podman && microdnf clean all && mkdir -p /sys/fs/cgroup/init