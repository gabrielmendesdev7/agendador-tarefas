package com.javanauta.agendadortarefas.business;

import com.javanauta.agendadortarefas.business.dto.request.TarefasRequest;
import com.javanauta.agendadortarefas.business.dto.response.TarefasResponse;
import com.javanauta.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.javanauta.agendadortarefas.business.mapper.TarefasConverter;
import com.javanauta.agendadortarefas.infrasctructure.entity.TarefasEntity;
import com.javanauta.agendadortarefas.infrasctructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendadortarefas.infrasctructure.exceptions.ResourceNotFoundException;
import com.javanauta.agendadortarefas.infrasctructure.repository.TarefasRepository;
import com.javanauta.agendadortarefas.infrasctructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasResponse gravarTarefas(String token, TarefasRequest request) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(request);
        entity.setDataCriacao(LocalDateTime.now());
        entity.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        entity.setEmailUsuario(email);
        var savedEntity = tarefasRepository.save(entity);
        return tarefaConverter.paraTarefaResponse(savedEntity);
    }

    public List<TarefasResponse> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial,
                                                            LocalDateTime dataFinal) {
        var tarefas = tarefasRepository
                .findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial,
                                                                 dataFinal,
                                                                 StatusNotificacaoEnum.PENDENTE);
        return tarefaConverter.paraListaTarefasResponse(tarefas);
    }

    public List<TarefasResponse> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extractEmailToken(token.substring(7));

        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefasResponse(listaTarefas);
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente " + id, e.getCause());
        }
    }

    public TarefasResponse alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o id: " + id));

            entity.setStatusNotificacaoEnum(status);
            tarefasRepository.save(entity);
            return tarefaConverter.paraTarefaResponse(entity);
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }

    public TarefasResponse updateTarefas(TarefasRequest request, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o id: " + id));

            tarefaUpdateConverter.updateTarefas(request, entity);
            tarefasRepository.save(entity);
            return tarefaConverter.paraTarefaResponse(entity);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao atualizar tarefa " + e.getCause());
        }
    }

}
