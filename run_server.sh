cd images/server-log/spring-server-log

mvn clean install

cd ../../server-application/spring-server-application

mvn clean install

cd ../../../docker-topologies

docker-compose up --build
