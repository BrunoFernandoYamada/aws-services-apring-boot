#!/usr/bin/env bash
export AWS_DEFAULT_REGION=us-east-1

# Cria a fila com o nome order-registration
aws --endpoint-url http://localhost:4566 sqs create-queue --queue-name order-registration

# Cria o topico com o nome messages-topic
aws --endpoint-url=http://localhost:4566 sns create-topic --name messages-topic

# Cria um bucket s3 com nome image-bucket
aws --endpoint-url=http://localhost:4566 s3api create-bucket --bucket image-bucket

