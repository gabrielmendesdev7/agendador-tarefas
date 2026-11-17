package com.javanauta.agendadortarefas.infrasctructure.messages.kafka.consumer;

import com.javanauta.agendadortarefas.business.TarefasService;
import com.javanauta.agendadortarefas.business.dto.StatusTarefaPayload;
import com.javanauta.agendadortarefas.infrasctructure.entity.MensageriaFalhaEntity;
import com.javanauta.agendadortarefas.infrasctructure.repository.MensageriaFalhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ConsumerKafka {

    private final ObjectMapper objectMapper;
    private final TarefasService tarefasService;
    private final MensageriaFalhaRepository repository;

    @RetryableTopic(
            attempts = "3",
            backOff = @BackOff(delay = 3000, multiplier = 2.0),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "status-notificacao", groupId = "grupo_agendador", containerFactory = "kafkaListenerContainerFactory")
    public void consumirStatusTarefa(String mensagem) {
        StatusTarefaPayload payload = objectMapper.readValue(mensagem, StatusTarefaPayload.class);

        tarefasService.alteraStatus(payload.getStatusNotificacaoEnum(), payload.getId());
    }

    @DltHandler
    public void recuperarMensagemFalha(String mensagem,
                                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                       Throwable exception) {
        MensageriaFalhaEntity mensageriaFalhaEntity = MensageriaFalhaEntity.builder()
                .topico(topic)
                .dataFalha(LocalDateTime.now())
                .stackTrace(exception.getMessage())
                .mensagem(mensagem)
                .build();

        repository.save(mensageriaFalhaEntity);
    }

}