package com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

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

    @LastModifiedDate
    private final LocalDateTime lastUpdated;

    @Version
    private final long version;
}