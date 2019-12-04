#!/bin/bash
#aws batch submit-job --job-name emitter --region us-east-1 --job-queue task-queue-test  --job-definition java --container-overrides '{"command": ["s3://webgames-app-distr/request-emitter/runner.sh"], "environment": [{"name": "ARGS", "value": "--nr 500 --rt 2000 --nb 5"}] }'

aws batch submit-job --job-name emitter --region us-east-1 \
 # shellcheck disable=SC1101
 --job-queue net-perf  \
 --job-definition java --container-overrides '{"vcpus":4, "memory":8000, "command": ["s3://webgames-app-distr/request-emitter/runner.sh"], "environment": [{"name": "ARGS", "value": "--nr 300 --rt 2000 --nb 5000 --u http://ec2-18-233-161-252.compute-1.amazonaws.com:8080/rtb/bids/nexage"}] }'