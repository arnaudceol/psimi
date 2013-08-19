#!/bin/bash

if [ $# == 2 ];
then
      INPUT_FILE=$1;
      OUTPUT_FILE=$2;
      echo "Input file: ${INPUT_FILE}"
      echo "Output file: ${OUTPUT_FILE}"
      mvn clean install -Penrich-file -Dmi.file=${INPUT_FILE} -Dmi.output=${OUTPUT_FILE} -Dspring.config=classpath:META-INF/mitab-enricher-spring.xml -Dmaven.test.skip
else
      echo ""
      echo "ERROR: wrong number of parameters ($#)."
      echo "usage: INPUT_FILE OUTPUT_FILE"
      echo "usage: INPUT_FILE: the name of the file to enrich."
      echo "usage: OUTPUT_FILE: the name of the file where to write the enriched interactions"
      echo ""
      exit 1
fi