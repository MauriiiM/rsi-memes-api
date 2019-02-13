# RSI Memes Server
### Started and created by RSI fall '18 JA, Mauricio Monsivais

## Prerequisites
- Have java JDK 8 or later
- Install Maven
- Note that if you're using IntelliJ, you'll want to install the Lombok plugin (available from IDE settings) and also
enable annotation processing.

## Executing
* Maven 3.5.4 needed to run code (and JDK 1.7+)
* Can also just run executable jar

# Setup for AWS
* Create an AWS RDS MySQL instance.
    * If testing on localhost, make sure security  is open and is not set to VPC
* Type in all respective fields in `application.yml`
* Create Elastic Beanstalk
* Upload executable to Beanstalk
  * Do `mvn package` (on IntelliJ, there's an action for it on one of the side tabs.)
  * When uploading updated code, increment version number in `pom.xml`

# Database
The database has to be setup separately from because this code will not detect if a table is not created
and create it, although it can probably be implemented into `DatabaseService.java` . 

## NOTE
**NEVER upload `application.yml`!! Find correct way to implement.**