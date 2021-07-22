TODO

embedded debezium + spring boot + RabbitMQ
	https://docs.spring.io/spring-cloud-stream-app-starters/docs/Einstein.SR6/reference/htmlsingle/#spring-cloud-stream-modules-cdc-debezium-source
	https://spring.io/blog/2020/12/14/case-study-change-data-capture-cdc-analysis-with-cdc-debezium-source-and-analytics-sink-in-real-time
	https://medium.com/swlh/change-data-capture-cdc-with-embedded-debezium-and-springboot-6f10cd33d8ec
	https://github.com/mike-costello/camel-debezium-examples
	https://github.com/jvalue/outboxer-postgres2rabbitmq
	https://github.com.cnpmjs.org/dilaverdemirel/spring-cloud-stream-outbox-extension
	https://github.com/adeo/lmru--spring-message-outbox

docker-compose up rabbit-mq&

Start Logstash, Elastic and Kibana

docker run -d -it --name es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.12.1

docker run -d -it --name kibana --link es:elasticsearch -p 5601:5601 docker.elastic.co/kibana/kibana:7.12.1

docker pull docker.elastic.co/logstash/logstash:7.12.1
docker run -d -it --name logstash --net=host logstash:7.12.1 -e 'input { rabbitmq {host => "localhost" port => 5672 queue => "logInQueue" durable => true} } output { elasticsearch { hosts => ["localhost"] } }'
docker start logstash
