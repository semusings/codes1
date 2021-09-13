#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
assert_value() {
  if [ -z "$1" ]; then
    echo "No args: $2"
    exit 1
  fi
}

option="${1}"
assert_value "$option" "option"
read -r -p "Enter release name: " ARGS
name=$ARGS
assert_value "$name" "name"
export TF_VAR_release_name=$name

run_terraform() {
  echo "Running the terraform..."
  TF_DIR="${SCRIPT_DIR}/terraform"
  if [ -z "$2" ]; then
    cd "$TF_DIR" && terraform "$1"
  else
    cd "$TF_DIR" && terraform "$1" "$2"
  fi
}

case ${option} in
--deploy)
  run_terraform 'init'
  run_terraform 'apply' '-auto-approve'
  ;;
--destroy)
  run_terraform 'init'
  run_terraform 'destroy' '-auto-approve'
  ;;
*)
  echo "$(basename "${0}"):usage: [ run <make> to see more details]"
  exit 1 # Command to come out of the program with status 1
  ;;
esac
