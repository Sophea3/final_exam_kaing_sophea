#!/bin/bash
set -e

# Start SSH
service ssh start
echo "SSH started on port 22"

# Build Spring Boot
cd /app
echo "Building Spring Boot application..."
./mvnw clean package -DskipTests -q 2>&1

# Start Spring Boot
echo "Starting Spring Boot on port 8082..."
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8082 &
sleep 15
echo "Spring Boot should be running"

# Start NGINX
echo "Starting NGINX on port 8080..."
nginx -g 'daemon off;'