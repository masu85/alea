package com.alea.pokeapi.pokemon.infraestructure.persistance.adapters;

import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.PokemonMongoDBO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PokemonMongoAdapter extends ReactiveMongoRepository<PokemonMongoDBO, Integer> {

}
