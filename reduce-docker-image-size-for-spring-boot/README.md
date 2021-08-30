# Reduce Docker Image Size For Spring Boot

- Option 1: Without `jlink` tool

```bash
echo -e no-jlink |  make build
```

- Option 2: Using `jlink` tool

```bash
echo -e jlink |  make build
```