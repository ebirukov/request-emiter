#!/bin/bash

aws s3 cp s3://webgames-app-distr/request-emitter/request-emiter.jar app.jar

java -jar app.jar "$@"