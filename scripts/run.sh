#!/usr/bin/env bash
cd ../taxation-calculator
mvn clean install -DskipTests
mvn spring-boot:run
