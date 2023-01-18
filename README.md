# Requirements

* JDK 17 (tested with eclipse-temurin)
* Docker (optional)

### Run natively

```bash
./gradlew bootRun
```

### Run with docker compose

```bash
docker-compose up
```

### Example curl

```bash
curl --request POST 'http://localhost:8080/visits' \
  --form 'csvFile=@"/Users/kaang/Downloads/flightright-case/input.csv"'
```
