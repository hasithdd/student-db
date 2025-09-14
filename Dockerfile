FROM openjdk:21-jdk-slim

WORKDIR /app

COPY lib/ ./lib/

COPY src/ ./src/

# COPY .env ./

RUN javac -cp "lib/*:." src/StudentManager.java

CMD ["java", "-cp", "lib/*:src", "StudentManager"]