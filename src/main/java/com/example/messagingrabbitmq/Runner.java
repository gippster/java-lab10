package com.example.messagingrabbitmq;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(RabbitTemplate rabbitTemplate, Receiver receiver) {
		this.rabbitTemplate = rabbitTemplate;
		this.receiver = receiver;
	}

	@Override
	public void run(String... args) throws Exception{
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Введите сообщение и нажмите Enter для отправки:");
			String message = scanner.nextLine();
			rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.fanoutExchangeName, "null", message);
			receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		}
	}
}
