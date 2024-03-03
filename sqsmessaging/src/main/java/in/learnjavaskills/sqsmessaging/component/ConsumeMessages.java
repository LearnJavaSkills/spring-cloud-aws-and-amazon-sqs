package in.learnjavaskills.sqsmessaging.component;

import in.learnjavaskills.sqsmessaging.dto.Employee;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.QueueAttributes;
import io.awspring.cloud.sqs.listener.Visibility;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * Consuming messages from the SQS queue.
 */
@Component
public class ConsumeMessages
{
    /**
     * SqsListener with the custom configuration on annotations.
     * @param message
     */
    @SqsListener(queueNames = "my-first-queue", id = "my-first-queue-id",
            maxConcurrentMessages = "10", pollTimeoutSeconds = "2",
            messageVisibilitySeconds = "2")
    public void listener(Message<Employee> message) {
        System.out.println("received message : "  + message.getPayload());
    }

    /**
     * SqsListener with multiple amazon SQS queue configuration. Instead of using queue name,
     * it's best practise to use the queue URL.
     * @param message
     */
    @SqsListener({"my-first-queue", "${my.second.queue.url}"})
    public void listener(String message) {
        System.out.println("received message : "  + message);
    }


    /**
     * SqsListern with the advance method parameters. if using acknowledgement then must configure
     * manual acknowledge in the SqsTemplate
     * @param message
     * @param messageHeaders
     * @param visibility
     * @param queueAttributes
     * @param originalMessage
     */
    @SqsListener("my-first-queue")
    public void listener(Message<Employee> message, MessageHeaders messageHeaders,
             Visibility visibility, QueueAttributes queueAttributes,
            software.amazon.awssdk.services.sqs.model.Message originalMessage) {
        System.out.println("received message : "  + message.getPayload());

        System.out.println("message header : " + messageHeaders.toString());

//        acknowledgement.acknowledge();

        visibility.changeTo(10);

        String queueUrl = queueAttributes.getQueueUrl();
        System.out.println("queue url : " + queueUrl);

        System.out.println("original message : " + originalMessage.toString());
    }


}
