terraform {
  required_providers {
    kubernetes = {
      source = "hashicorp/kubernetes"
      version = "~>2.3.2"
    }
    helm = {
      source = "hashicorp/helm"
      version = "~>2.2.0"
    }
    aws = {
      source = "hashicorp/aws"
      version = "~>3.52.0"
    }
  }
}

provider "aws" {
  region = var.cluster_region
}

provider "kubernetes" {
  config_path = var.cluster_kube_config
}

provider "helm" {
  kubernetes {
    config_path = var.cluster_kube_config
  }
}

data "aws_region" "current" {}

resource "random_string" "suffix" {
  length  = 5
  upper   = false
  lower   = true
  number  = false
  special = false
}