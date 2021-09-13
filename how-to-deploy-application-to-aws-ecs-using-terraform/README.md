# How to deploy application to AWS ECS using terraform

## Build Docker

```bash
mvn clean install
```

## Setup terraform variables

```bash
cp -R terraform.tfvars.example terraform.tfvars
vi terraform.tfvars
```
