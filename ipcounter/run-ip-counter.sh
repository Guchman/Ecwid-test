#!/usr/bin/env bash

JAR=build/libs/ipcounter.jar

if [ ! -f "$JAR" ]; then
  echo "Building jar"
  ../gradlew :ipcounter:jar
fi

java -Xms1G -Xmx1G -jar "$JAR" "$1"