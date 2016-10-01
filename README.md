# Introduction
The project spring-rest-movie provides REST services for create, update, delete and search for movies that you like and want keeping as a record.

# Technologies
Built using maven, java 8, spring-boot 1.4.1, spring-boot-actuator, spring-mvc, spring-data, swagger 2, querydsl and lombok accessing NoSQL MongoDB 3.0.6. 

# Travis CI (Continuous Integration)
Using Travis as CI accessing https://travis-ci.org/eacarvalho/spring-rest-movie

# How start the project
mvn spring-boot:run -Dserver.port=8080

# How access the project
http://localhost:8080/

- POST    http://localhost:8080/movies
- PUT     http://localhost:8080/movies/1
- GET     http://localhost:8080/movies
- GET     http://localhost:8080/movies/1
- GET     http://localhost:8080/movies/filter?tittle=Taken
- DELETE  http://localhost:8080/movies/1
- GET     http://localhost:8080/types
- GET     http://localhost:8080/genres

Sample Movie JSON POST:

```
{
  "tittle":"Taken",
  "originalTittle":"Taken",
  "duration":116,
  "type":"Movie",
  "genres": ["Action"],
  "releasedDate":"2015-11-28T18:25:43Z",
  "plot":"Write down your description here",
  "rating":5
}
```

# API RESTful
Using Swagger 2 to document the RESTFul API accessing http://localhost:8080/swagger-ui.html

# How check health of the app (actuator)
- HEALTH      http://localhost:8080/health
- INFO        http://localhost:8080/info
- AUTOCONFIG  http://localhost:8080/autoconfig
- BEANS       http://localhost:8080/beans
- CONFIGPROPS http://localhost:8080/configprops
- DUMP        http://localhost:8080/dump
- METRICS     http://localhost:8080/metrics
- MAPPINGS    http://localhost:8080/mappings
- TRACE       http://localhost:8080/trace 

# Setting up the mongodb
1. Start mongodb 3.0.6 as "mongod --config /usr/local/etc/mongod.conf"
2. Connect to mongodb as "mongo"
3. Create database and user as following:

```
use movie

db.createUser(
  {
    user: "mongo_movie",
    pwd: "123",
    roles: [
      {
        role: "userAdmin",
        db: "movie"
      }
    ]
  }
)
```
