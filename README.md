# RSI Memes Server

## Prerequisites
- Have java JDK 10 or later
- install Maven
- Note that if you're using IntelliJ, you'll want to install the Lombok plugin (available from IDE settings) and also enable annotation processing.

## Executing
* Maven 3.5.4 needed to run code (and JDK 1.7+)
* or without having Maven installed, Maven compiled jar can be executed with: `mvnw spring-boot:run `

# Setup for AWS
Create an AWS RDS MySQL instance. When completed, type in all respective fields in `application.yml`. Make sure your AWS
RDS instance has security settings set correctly (specifically _Public accessibility_).