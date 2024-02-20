package com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "pokemon")
@Getter
@Builder
public class PokemonMongoDBO {

    @MongoId
    private final Integer id;
    private final String name;
    private final Integer height;
    private final Integer weight;
    private final Integer baseExperience;


}