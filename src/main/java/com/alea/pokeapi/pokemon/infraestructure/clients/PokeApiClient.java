package com.alea.pokeapi.pokemon.infraestructure.clients;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonExternalData;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.PokemonDetailDTO;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.PokemonListDTO;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.mappers.PokemonPokeApiClientMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.net.URI;

import static java.lang.String.format;

@Component
public class PokeApiClient implements PokemonExternalData {

    private final String baseUri;
    private final WebClient webClient;
    private final PokemonPokeApiClientMapper pokemonPokeApiClientMapper;

    public PokeApiClient(@Value("${pokeapi.base-url}") String pokeApiUrl,
                         PokemonPokeApiClientMapper pokemonPokeApiClientMapper) {
        this.baseUri = pokeApiUrl;
        this.webClient = WebClient.create();
        this.pokemonPokeApiClientMapper = pokemonPokeApiClientMapper;
    }

    public Mono<Pokemon> getPokemon(Integer id) {
        return webClient.get()
                .uri(URI.create(format("%s/pokemon/%s", baseUri, id)))
                .retrieve()
                .bodyToMono(PokemonDetailDTO.class)
                .map(pokemonPokeApiClientMapper::pokeApiToPokemon);
    }

    @Override
    public Mono<Integer> countPokemon() {
        return webClient.get()
                .uri(URI.create(format("%s/pokemon/?offset=0&limit=1", baseUri)))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.get("count").asInt());
    }

    @Override
    public Flux<Tuple2<String, String>> getExistingPokemon(Integer offset, Integer limit) {
        return webClient.get()
                .uri(URI.create(format("%s/pokemon/?offset=%s&limit=%s", baseUri, offset, limit)))
                .retrieve()
                .bodyToFlux(PokemonListDTO.class)
                .flatMapIterable(PokemonListDTO::getResults)
                .map(result -> Tuples.of(result.getName(), result.getUrl()));
    }
}
