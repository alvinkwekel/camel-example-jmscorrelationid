package org.apache.camel.example;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
public class MyConfig {

    @Bean
    public ActiveMQComponent activemq(ConnectionFactory connectionFactory) {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(connectionFactory);
        activeMQComponent.setRequestTimeout(3000);
        return activeMQComponent;
    }

}
