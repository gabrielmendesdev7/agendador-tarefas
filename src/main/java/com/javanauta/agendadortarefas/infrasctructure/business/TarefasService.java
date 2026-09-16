package com.javanauta.agendadortarefas.infrasctructure.business;

import com.javanauta.agendadortarefas.infrasctructure.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrasctructure.business.mapper.TarefasConverter;
import com.javanauta.agendadortarefas.infrasctructure.entity.TarefasEntity;
import com.javanauta.agendadortarefas.infrasctructure.enums.StatusNotificacaoEnum;
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

    public TarefasDTO gravarTarefas(String token, TarefasDTO dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);
        var savedEntity = tarefasRepository.save(entity);
        return tarefaConverter.paraTarefaDTO(savedEntity);
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial,
                                                            LocalDateTime dataFinal) {
        var tarefas = tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal);
        return tarefaConverter.paraListaTarefasDTO(tarefas);
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extractEmailToken(token.substring(7));

        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefasDTO(listaTarefas);
    }

}
