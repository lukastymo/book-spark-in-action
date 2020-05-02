#!/bin/bash

: "${APP_JAR_PATH?Need to set APP_JAR_PATH}"

spark-submit \
  --class ch03.Example02_Aggregate2 \
  --master local[*] \
  "${APP_JAR_PATH}" \
  "/Users/lukasz/dev/github/06 books/book-spark-in-action/target/output.json"
