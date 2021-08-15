# Reduce Docker Image Size For Spring Boot

In this demo, I will discuss the possible ways to reduce docker image size for spring boot application.

## Build docker images

- Option 1: Without `jlink` tool
```bash
echo -e no-jlink |  make build
```

- Option 2: Using `jlink` tool
```bash
echo -e jlink |  make build
```