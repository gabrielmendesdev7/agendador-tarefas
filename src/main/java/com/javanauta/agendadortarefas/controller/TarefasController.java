package com.javanauta.agendadortarefas.controller;

import com.javanauta.agendadortarefas.business.TarefasService;
import com.javanauta.agendadortarefas.business.dto.request.TarefasRequest;
import com.javanauta.agendadortarefas.business.dto.response.TarefasResponse;
import com.javanauta.agendadortarefas.infrasctructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasResponse> gravarTarefas(@RequestBody TarefasRequest request,

                                                         @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefas(token, request));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasResponse>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefasResponse>> buscaTarefasPorEmail(@RequestHeader("Authorization") String token) {
        var tarefas = tarefasService.buscaTarefasPorEmail(token);
        return ResponseEntity.ok(tarefas);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam String id) {
        tarefasService.deletaTarefaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<TarefasResponse> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum statusNotificacaoEnum,
                                                              @RequestParam("id") String id) {
        TarefasResponse response = tarefasService.alteraStatus(statusNotificacaoEnum, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<TarefasResponse> updateTarefas(@RequestBody TarefasRequest request, @RequestParam("id") String id) {
        TarefasResponse updatedTarefa = tarefasService.updateTarefas(request, id);
        return ResponseEntity.ok(updatedTarefa);
    }

}
