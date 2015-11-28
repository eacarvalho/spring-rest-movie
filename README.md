# spring-rest-movie

spring boot, spring data (querydsl), spring mvc, mongoldb

Start mongodb as "mongod --config /usr/local/etc/mongod.conf" Connect to mongodb as "mongo"

Create database and user as following:

db.createUser( { user: "mongo_movie", pwd: "123", roles: [ { role: "userAdmin", db: "movie" } ] } )
