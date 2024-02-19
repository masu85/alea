package com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Document("pokemon")
public class PokemonMongoDBO {

    @MongoId
    private String internalId;
    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;

    @LastModifiedDate
    private LocalDateTime lastUpdated;

    @Version
    private long version;
}