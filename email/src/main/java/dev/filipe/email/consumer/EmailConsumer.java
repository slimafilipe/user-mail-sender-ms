package dev.filipe.email.consumer;

import dev.filipe.email.domain.EmailModel;
import dev.filipe.email.dto.EmailDto;
import dev.filipe.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${EMAIL_QUEUE}")
    public void  listenEmailQueue(@Payload EmailDto emailDto) {
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
    }
}
