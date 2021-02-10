# Minesweeper API

Running online at https://minesweeper-7c885.herokuapp.com/

# Build and testing
## How to build
```
$ ./gradlew build
```

## How to run
```
$ ./gradlew bootRun
```

## How to test
```
$ ./gradlew test
```

# API

## Swagger
- Open https://minesweeper-7c885.herokuapp.com/swagger-ui.html to see all controllers and endpoints available

## Create a new game
```
$ curl --location --request POST 'http://localhost:8080/games/' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "size": 1
  }'
```

## Game Details
```
$ curl --location --request GET 'http://localhost:8080/games/?id=1'
```

## Open position
```
$ curl --location --request PATCH 'http://localhost:8080/games/2/open' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "x": 1,
      "y": 1
  }'
```

## Flag position
```
$ curl --location --request PATCH 'http://localhost:8080/games/2/flag' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "x": 4,
      "y": 4
  }'
```
