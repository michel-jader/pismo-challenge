# Pismo Challenge

## Start Up Everything (App + Database)

### Docker + Docker Compose

From pismo-challenge root folder, run the command below! 

```
docker compose up -d
```

PS: It will expose port 3306 for MySQL 8.0 database and port 8080 from Pismo Challenge App


## Build and Tests 

### Builds everything and runs all tests

From pismo-challenge root folder, execute command below:

```
./gradlew build
```

### Just build project, skipping tests

From pismo-challenge root folder, execute command below:

```
./gradlew build -x test
```

## API Documentation

### Access address below in you browser to reach swagger ui
```
http://localhost:8080/swagger-ui/index.html
```

### Access address below in you browser for OpenAPI definitions
```
http://localhost:8080/v3/api-docs
```

### API use examples

Creates new account
```
curl --location 'http://localhost:8080/accounts' \
--header 'Content-Type: application/json' \
--data '{
"document_number": "000000001"
}'
```

Retrieves created account
```
curl --location 'http://localhost:8080/accounts/1'
```

Creates new transaction
```
curl --location 'http://localhost:8080/transactions' \
--header 'Content-Type: application/json' \
--data '{
"account_id": 1,
"operation_type_id": 4,
"amount": 256.2
}'
```

