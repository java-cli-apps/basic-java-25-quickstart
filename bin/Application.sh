#!/usr/bin/env bash

get_application_dir() {
    dirname "$(dirname "$(realpath "$1")")"
}

get_application_file() {
    startup_script=$(basename "$1")
    echo "${startup_script/sh/java}"
}

app_dir=$(get_application_dir "$0")
app_file=$(get_application_file "$0")

APP_DIR=${app_dir} "${app_dir}/src/${app_file}" "$@"
