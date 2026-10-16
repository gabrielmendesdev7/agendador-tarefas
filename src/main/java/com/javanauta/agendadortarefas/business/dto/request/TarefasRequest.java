package com.javanauta.agendadortarefas.business.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TarefasRequest(String nomeTarefa,
                             String descricao,
                             @JsonFormat(shape = JsonFormat.Shape.STRING,
                                     pattern = "dd-MM-yyyy HH:mm:ss")
                             LocalDateTime dataEvento) {

}
