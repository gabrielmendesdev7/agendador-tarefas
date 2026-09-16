package com.javanauta.agendadortarefas.infrasctructure.business.mapper;

import com.javanauta.agendadortarefas.infrasctructure.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrasctructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefaDTO(TarefasEntity entity);

    List<TarefasEntity> paraTarefasEntity(List<TarefasDTO> dtos);

    List<TarefasDTO> paraListaTarefasDTO(List<TarefasEntity> entities);


}
