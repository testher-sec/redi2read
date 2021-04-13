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

appendonly.aof??? 
