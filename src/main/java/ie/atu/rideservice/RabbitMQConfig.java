package ie.atu.rideservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue rideQueue() {
        return new Queue("rideQueue", true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("appExchange");
    }

    @Bean
    public Binding rideBinding(Queue rideQueue, DirectExchange exchange) {
        return BindingBuilder.bind(rideQueue).to(exchange).with("ride");
    }
}
