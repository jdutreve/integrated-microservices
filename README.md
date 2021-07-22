### Context
- This repository is an implementation of the [Ambassador Architecture](https://jdutreve.medium.com/monolith-or-microservices-neither-combine-both-with-integrated-microservices-5265594c3d59) (aka Integrated Microservices).  
- There is a [companion article]() that details some interesting parts.
- The use case is not that important, and means to create a customer with a credit rating score.
- It uses Spring Boot, Spring Cloud Stream, RabbitMQ, and the ELK stack for Observability. Every state is stored **in-memory only**.

### Starting RabbiMQ, Logstash, ElasticSearch and Kibana

```shell
docker-compose up rabbit-mq

docker run -d -it --name es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.12.1

docker run -d -it --name kibana --link es:elasticsearch -p 5601:5601 docker.elastic.co/kibana/kibana:7.12.1

docker pull docker.elastic.co/logstash/logstash:7.12.1
docker run -d -it --name logstash --net=host logstash:7.12.1 -e 'input { rabbitmq {host => "localhost" port => 5672 queue => "logInQueue" durable => true} } output { elasticsearch { hosts => ["localhost"] } }'
docker start logstash
```