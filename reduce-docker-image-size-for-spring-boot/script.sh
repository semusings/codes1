#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

assert_value() {

  if [ -z "$1" ]; then
    echo "No args: $2"
    exit 1
  fi

}

option="${1}"
name="${2}"
validate() {
  assert_value "$option" "option"
  assert_value "$name" "name"
}

stack="reduce-docker-image-size-for-spring-boot-${name}"
tag="latest"

build_docker() {

  docker build \
    -t "${stack}:${tag}" \
    --file "${SCRIPT_DIR}/dockerfiles/${name}.dockerfile" .

}

run_docker() {

  docker run "${stack}:${tag}"

}

case ${option} in
--build)
  validate
  build_docker
  ;;
--run)
  validate
  run_docker
  ;;  
*)
  echo "$(basename "${0}"):usage: [--build] | [--run]"
  exit 1 # Command to come out of the program with status 1
  ;;
esac