package com.alea.pokeapi.pokemon.infraestructure.clients;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.PokemonPokeApiDTO;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.mappers.PokemonPokeApiClientMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.lang.String.format;

@Component
public class PokeApiClient {

    private final String baseUri;
    private final WebClient webClient;

    public PokeApiClient(@Value("${pokeapi.base-url}") String pokeApiUrl) {
        this.baseUri = pokeApiUrl;
        this.webClient = WebClient.create();
    }

    public Mono<Integer> countPokemons() {
        return webClient.get()
                .uri(URI.create(format("%s/pokemon", baseUri)))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.get("count").asInt());
    }

    public Mono<Pokemon> getPokemon(Integer id) {
        return webClient.get()
                .uri(URI.create(format("%s/pokemon/%s", baseUri, id)))
                .retrieve()
                .bodyToMono(PokemonPokeApiDTO.class)
                .map(PokemonPokeApiClientMapper.Mapper::pokeApiToPokemon);
    }
}
