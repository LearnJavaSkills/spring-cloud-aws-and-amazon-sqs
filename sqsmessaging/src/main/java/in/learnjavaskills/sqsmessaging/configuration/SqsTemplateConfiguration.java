package in.learnjavaskills.sqsmessaging.configuration;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.TemplateAcknowledgementMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

@Configuration
public class SqsTemplateConfiguration
{
    /**
     * Custom configuration of SqsTemplate
     * @param sqsAsyncClient
     * @return instance of the SqsTemplate
     */
    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient)
    {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .configure(options -> options
                        .acknowledgementMode(TemplateAcknowledgementMode.MANUAL) // acknowledge manually
                        .defaultQueue("my-first-queue")
                        .defaultPollTimeout(Duration.ofSeconds(5))
                        .defaultMaxNumberOfMessages(5)
                )
                .build();
    }

}
