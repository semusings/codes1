terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "~>3.52.0"
    }
  }
}

provider "aws" {
  region = var.ecs_region
}

data "aws_region" "current" {}

data "aws_vpc" "selected" {
  id = var.vpc_id
}

data "aws_subnet_ids" "selected" {
  vpc_id = data.aws_vpc.selected.id
}
