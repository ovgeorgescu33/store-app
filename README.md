#### Prerequisites
- Java 11
- Maven 3.6.3 or more
- Docker (optional)
- Lombok plugin (optional)

#### Build
Open a terminal in project root directory and run `mvn clean install`.
    
#### Running the application

##### Using spring-boot maven plugin
Open a terminal (or use IDEA) and run `mvn spring-boot:run` in `store-service` module.

##### From IDEA
Import as a Maven project and run it as a Spring Boot Application using `StoreApplication` class as an entry point.

##### As a jar
Copy generated jar from `store-service/target` in the desired location and run:

`java -jar store-service-{VERSION}.jar` 

##### Using Docker
Open a terminal in project root directory

Build the image: 

`docker build --tag store-service:0.0.1 .`

Start the container:
 
`docker run --public 8080:8080 --detach --name store-service store-service:0.0.1`

To stop and remove the container:

`docker rm --force store-service`

For more information about working with docker containers check [this guide](https://docs.docker.com/get-started/part2/)

#### Modules
- store-service: web application exposing a REST API to handle `Product` and `Order` entities
- store-common:  library to hold request/response objects for store api and some simple validation logic
- store-component-test module to hold component/functional tests (TODO)
#### Usage
Build and launch the application. Swagger will be available at http://localhost:8080/swagger

