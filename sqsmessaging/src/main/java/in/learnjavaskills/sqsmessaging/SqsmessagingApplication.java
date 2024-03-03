package in.learnjavaskills.sqsmessaging;

import in.learnjavaskills.sqsmessaging.dto.Employee;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;


@SpringBootApplication
public class SqsmessagingApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(SqsmessagingApplication.class, args);
		SqsTemplate sqsTemplate = configurableApplicationContext.getBean(SqsTemplate.class);

//		sendMessageSqsSendOptions(sqsTemplate);
		sendMessagePayload(sqsTemplate);
//		receiveMessage(sqsTemplate);
	}

    /**
     * sending the POJO message to the amazon SQS
     * @param sqsTemplate
     */
	private static void sendMessagePayload(SqsTemplate sqsTemplate) {

		Employee employee = new Employee("name", "designation", "Laptop");

		System.out.println("Sending Message to SQS queue");

		SendResult<Object> sendResult = sqsTemplate.send(to ->to.queue("my-first-queue")
				.payload(employee)
				.header("header1", "header-value1")
				.headers(Map.of("My-second-header", "my-second-header-value"))
				.delaySeconds(1));

		System.out.println("Successfully send message on SQS queue topic \n message id : " + sendResult.messageId() +
			   " \n endpoint : " + sendResult.endpoint() +
			   " \n message : " + sendResult.message().toString() +
			   " \n additional message : " + sendResult.additionalInformation());
	}

    /**
     * sending string message to SQS
     * @param sqsTemplate
     */
	private static void sendMessageSqsSendOptions(SqsTemplate sqsTemplate) {
		System.out.println("Sending Message to SQS queue");

		SendResult<Object> sendResult = sqsTemplate.send(to -> to.queue("my-first-queue")
				.payload("Hello,  My first message to amazon SQS")
				.header("header1", "header-value1")
				.delaySeconds(2));

		System.out.println("Successfully send message on SQS queue topic \n message id : " + sendResult.messageId() +
						   " \n endpoint : " + sendResult.endpoint() +
						   " \n message : " + sendResult.message().toString() +
						   " \n additional message : " + sendResult.additionalInformation());
	}


    /**
     * receiving message from the amazon sqs
     * @param sqsTemplate
     */
	private static void receiveMessage(SqsTemplate sqsTemplate) {
		System.out.println("receiving message");
		Optional<Message<Employee>> receivedMessage = sqsTemplate.receive(from -> from.queue("my-first-queue")
				.visibilityTimeout(Duration.ofSeconds(10))
				.pollTimeout(Duration.ofSeconds(10)), Employee.class);


		receivedMessage.ifPresent(message -> {
			Employee employee = message.getPayload();
			MessageHeaders headers = message.getHeaders();
			System.out.println("employee : " + employee + "\n " + headers.toString());
		});
	}
}
