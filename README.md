# Introduction
Project spring-rest-movie provide REST services for create, update, delete and search for movies that you like and want keeping as a record

# Technologies
Uses spring-boot, spring-mvc and spring-data (querydsl) accessing NoSQL MongoDB. 

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

{
  "title":"007",
  "originalTitle":"07",
  "duration":116,
  "type":"Movie",
  "category:"Action",
  "date":"2015-11-28T18:25:43Z",
  "plot":"Write down your description here",
  "directors":null,
  "rating":3
}

# Setting up the mongodb
1. Start mongodb as "mongod --config /usr/local/etc/mongod.conf"
2. Connect to mongodb as "mongo"
3. Create database and user as following:

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
