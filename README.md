echo "# redi2read" >> README.md
git init
git commit --allow-empty -m "git: initial empty commit"
git add README.md
git commit -m "feat: spring boot + redis initializr app"
git branch -M main

Git submodules, if you haven't encountered them, are a way to nest a git repo within another git repo
https://git-scm.com/book/en/v2/Git-Tools-Submodules
git submodule add git@github.com:redis-developer/redismod-docker-compose.git docker
git submodule add git@github.com:redis-developer/redi2read-data.git src/main/resources/data

evega@Esthers-MacBook-Pro ~ $ redis-cli shutdown
evega@Esthers-MacBook-Pro ~ $ redis-cli 

127.0.0.1:6379> PING
127.0.0.1:6379> MODULE LIST 

Fail fast, in case of error it returns nothing
127.0.0.1:6379> SET myname "Brian"
OK
127.0.0.1:6379> GET myname
"Brian"
127.0.0.1:6379> TYPE myname
string

Data from ram rather than disk. It's durable, data is persisted but has a copy in ram
Append only format. aof

appendonly.aof

-------------------------
https://spring.io/projects/spring-data
Spring Data Redis provides access to Redis from Spring applications. It offers both low-level and high-level abstractions for interacting with Redis.

RedisTemplate, a class that provides a thread-safe bridge between Spring and Redis Commands
 
/mvnw clean spring-boot:run


curl --location --request POST 'http://localhost:8080/api/redis/strings' \
 --header 'Content-Type: application/json' \
 --data-raw '{ "database:redis:creator": "Salvatore Sanfilippo" }'

We will use the RedisTemplate instance template opsForValue() method to get an instance of ValueOperations, which provides methods to execute operations performed on simple values (or Strings in Redis terminology).

evega@Esthers-MacBook-Pro redi2read (main) $ redis-cli MONITOR

Let’s test the RoleRepository by using a CommandLineRunner implementation. A Spring CommandLineRunner is an interface that tells the Spring container that the run method needs to be executed upon startup.


127.0.0.1:6379> KEYS com.redislabs.edu.redi2read.models.Role*
1) "com.redislabs.edu.redi2read.models.Role"
2) "com.redislabs.edu.redi2read.models.Role:8b2ecb12-531a-4500-9143-f9b13eab07d2"
3) "com.redislabs.edu.redi2read.models.Role:d87db9a1-8714-4c01-bacd-345ee679166f"
1


127.0.0.1:6379> HGETALL "com.redislabs.edu.redi2read.models.Role:d87db9a1-8714-4c01-bacd-345ee679166f"
1) "_class"
2) "com.redislabs.edu.redi2read.models.Role"
3) "id"
4) "d87db9a1-8714-4c01-bacd-345ee679166f"
5) "name"
6) "customer"

127.0.0.1:6379> TYPE "com.redislabs.edu.redi2read.models.Role"
set
127.0.0.1:6379> SMEMBERS "com.redislabs.edu.redi2read.models.Role"
1) "d87db9a1-8714-4c01-bacd-345ee679166f"
2) "8b2ecb12-531a-4500-9143-f9b13eab07d2"








