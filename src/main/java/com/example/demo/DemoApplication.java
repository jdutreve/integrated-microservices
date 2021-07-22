package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * cd event-driven-spring-boot
 * docker-compose up rabbit-mq&
 *
 * cd ../spring_amqp
 * docker run -d -it --name es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.12.1
 * docker run -d -it --name kibana --link es:elasticsearch -p 5601:5601 docker.elastic.co/kibana/kibana:7.12.1
 * docker pull docker.elastic.co/logstash/logstash:7.12.1
 * docker run -d -it --name logstash --net=host logstash:7.12.1 -e 'input { rabbitmq {host => "localhost" port => 5672 queue => "logInQueue" durable => true} } output { elasticsearch { hosts => ["localhost"] } }'
 * docker start logstash
 *
 * DemoApplication Command line parameters :
 * 		-ea -Dspring.profiles.active=async/sync -Dspring.main.allow-bean-definition-overriding=true
 *
 * 	http://localhost:8080/start
 */
@SpringBootApplication
@ComponentScan(basePackages = {
	"com.example.demo.client",					// Business Flow : 'client' GET /start that calls
	"com.example.demo.frontend",				// 		'frontend' that calls
			"com.example.demo.customer",		//			'customer' & 'scoring' inside some SAGAs (create & update).
			"com.example.demo.scoring"
})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}