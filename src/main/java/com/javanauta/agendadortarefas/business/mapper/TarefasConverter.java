package com.javanauta.agendadortarefas.business.mapper;

import com.javanauta.agendadortarefas.business.dto.request.TarefasRequest;
import com.javanauta.agendadortarefas.business.dto.response.TarefasResponse;
import com.javanauta.agendadortarefas.infrasctructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasRequest request);

    TarefasResponse paraTarefaDTO(TarefasEntity entity);

    List<TarefasEntity> paraTarefasEntity(List<TarefasResponse> responses);

    List<TarefasResponse> paraListaTarefasDTO(List<TarefasEntity> entities);


}
