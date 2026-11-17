package com.javanauta.agendadortarefas.infrasctructure.repository;

import com.javanauta.agendadortarefas.infrasctructure.entity.MensageriaFalhaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensageriaFalhaRepository extends MongoRepository<MensageriaFalhaEntity, String> {

}
