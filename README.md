# Introduction
Project spring-rest-movie provides REST services for create, update, delete and search for movies that you like and want keeping as a record.

# Technologies
Uses spring-boot 1.3, spring-mvc, spring-data (querydsl) and lombok accessing NoSQL MongoDB 3.0.6. 

# Architecture
3 layers (controller, service, repository).

# How start the project
mvn spring-boot:run -Dserver.port=8080

# How access the project
http://localhost:8080/

- POST  http://localhost:8080/movie
- GET   http://localhost:8080/movie
- GET   http://localhost:8080/movie/1

Sample JSON POST:

```
{
  "title":"007",
  "originalTitle":"007",
  "duration":116,
  "type":"Movie",
  "category:"Action",
  "date":"2015-11-28T18:25:43Z",
  "plot":"Write down your description here",
  "directors":null,
  "rating":3
}
```

# Setting up the mongodb
1. Start mongodb 3.0.6 as "mongod --config /usr/local/etc/mongod.conf"
2. Connect to mongodb as "mongo"
3. Create database and user as following:

```
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
