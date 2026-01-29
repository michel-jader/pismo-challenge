# Pismo Challenge

*** Before running below steps, make sure you have Java21, Docker and Docker Compose installed ***

## Services

### MySQL
- 1) Access project root folder "pismo-challenge". E.g. ```cd ~/projects/pismo-challenge```
- 2) Run below docker compose command to startup mysql 5.7 docker container (it will use port 3308):

```
docker compose -f src/main/docker/mysql.yml up -d
```

## Build and Tests 

### Builds everything and runs all tests

From our project pismo-challenge root folder, execute below command:

```
./gradlew build
```

### Just build project, skipping tests

From our project pismo-challenge root folder, execute below command:

```
./gradlew build -x test
```


## Start up application

### Running from command line

java -jar your-application.jar --spring.profiles.active=prod

### Running application with docker 

TODO:...........


## API Documentation

### Access below address in you browser to reach swagger ui
```
http://localhost:8080/swagger-ui/index.html
```

### Access below address in you browser for OpenAPI definitions
```
http://localhost:8080/v3/api-docs
```

