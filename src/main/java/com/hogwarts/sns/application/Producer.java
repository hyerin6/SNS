package com.hogwarts.sns.application;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {

	private final RabbitTemplate rabbitTemplate;

	public void sendTo(String message) {
		this.rabbitTemplate.convertAndSend("CREATE_POST_QUEUE", message);
	}

}
