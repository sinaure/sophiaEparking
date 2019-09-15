package com.sinaure.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinaure.service.ParkingService;


@Component
public class FirstListener {
    public final static String QUEUE_ROUTINGKEY = "routingKey1-boot";
    private final static String QUEUE_NAME = "parking-exchange";
    public final static String EXCHANGE_NAME = "parking-exchange";
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Integer counter = 0;
    @Autowired
    private ParkingService parkingService;
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QUEUE_NAME, durable = "true"),
            exchange = @Exchange(value = EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
            key = QUEUE_ROUTINGKEY))
    public void receiveMessage(String message) {
        logger.info("Received <{}>" + message);
        //perform availability check
        counter++;
    }

    public Integer getCounter() {
        return counter;
    }

    public void initCounter() {
        this.counter = 0;
    }

}
