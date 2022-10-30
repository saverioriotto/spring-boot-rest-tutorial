FROM maven:3.8.2-jdk-8

LABEL maintainer="Saverio Riotto info@saverioriotto.it"
WORKDIR /spring-app-rest
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run