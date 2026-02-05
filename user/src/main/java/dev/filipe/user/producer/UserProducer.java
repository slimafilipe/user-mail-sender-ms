package dev.filipe.user.producer;

import dev.filipe.user.domain.UserModel;
import dev.filipe.user.dto.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    @Value("${EMAIL_QUEUE}")
    private String routingKey;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(UserModel user) {
        var emailDto = new EmailDto();
        emailDto.setUserId(user.getId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setEmailSubject("Seja bem vindo!");
        emailDto.setBody("Olá " + user.getName() + " sejam muito bem vindo! \n\nAgradecemos demais por você fazer parte dessa jornada!");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

}
