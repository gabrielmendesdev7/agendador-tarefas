package com.javanauta.agendadortarefas.business.mapper;

import com.javanauta.agendadortarefas.business.dto.request.TarefasRequest;
import com.javanauta.agendadortarefas.business.dto.response.TarefasResponse;
import com.javanauta.agendadortarefas.infrasctructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "emailUsuario", ignore = true)
    @Mapping(target = "dataAlteracao", ignore = true)
    @Mapping(target = "statusNotificacaoEnum", ignore = true)
    TarefasEntity paraTarefaEntity(TarefasRequest request);

    TarefasResponse paraTarefaResponse(TarefasEntity entity);

    List<TarefasEntity> paraTarefasEntity(List<TarefasResponse> responses);

    List<TarefasResponse> paraListaTarefasResponse(List<TarefasEntity> entities);


}
