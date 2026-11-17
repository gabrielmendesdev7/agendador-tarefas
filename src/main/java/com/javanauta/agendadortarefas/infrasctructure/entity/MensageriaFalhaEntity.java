package com.javanauta.agendadortarefas.infrasctructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("mensageria-falha")
public class MensageriaFalhaEntity {

    @Id
    private String id;
    private String topico;
    private String mensagem;
    private String stackTrace;
    private LocalDateTime dataFalha;

}
