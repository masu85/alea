package com.alea.pokeapi.pokemon.infraestructure.clients;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonExternalData;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.PokemonDetailDTO;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.PokemonListDTO;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.mappers.PokemonPokeApiClientMapper;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
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
    private final JsonFactory jsonFactory;
    private final PokemonPokeApiClientMapper pokemonPokeApiClientMapper;

    public PokeApiClient(@Value("${pokeapi.base-url}") String pokeApiUrl,
                         WebClient webClient,
                         PokemonPokeApiClientMapper pokemonPokeApiClientMapper) {
        this.baseUri = pokeApiUrl;
        this.webClient = webClient;
        this.jsonFactory = new JsonFactory();
        this.pokemonPokeApiClientMapper = pokemonPokeApiClientMapper;
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
    public Flux<Tuple2<String, String>> getExistingPokemons(Integer offset, Integer limit) {
        return webClient.get()
                .uri(URI.create(format("%s/pokemon/?offset=%s&limit=%s", baseUri, offset, limit)))
                .retrieve()
                .bodyToFlux(PokemonListDTO.class)
                .flatMapIterable(PokemonListDTO::getResults)
                .map(result -> Tuples.of(result.getName(), result.getUrl()));
    }

    @Override
    @SneakyThrows
    public Flux<Pokemon> getPokemon(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(String.class)
                .map(this::parsePokemonDetails)
                .map(pokemonPokeApiClientMapper::pokeApiToPokemon);
    }

    @SneakyThrows
    private PokemonDetailDTO parsePokemonDetails(String json) {

        int nestedObjectDepth = 0;
        boolean insideNestedObject = false;
        var pokemonDTO = PokemonDetailDTO.builder();

        try (JsonParser jsonParser = jsonFactory.createParser(json)) {

            //Search the beggining of JSON
            while (jsonParser.nextToken() != JsonToken.START_OBJECT) {}

            //Read JSON fields
            while (jsonParser.nextToken() != null) {

                if (jsonParser.currentToken() == JsonToken.START_OBJECT || jsonParser.currentToken() == JsonToken.START_ARRAY) {
                    nestedObjectDepth++;
                    insideNestedObject = true;
                    continue;
                } else if (jsonParser.currentToken() == JsonToken.END_OBJECT || jsonParser.currentToken() == JsonToken.END_ARRAY) {
                    nestedObjectDepth--;
                    if (nestedObjectDepth == 0) {
                        insideNestedObject = false;
                    }
                    continue;
                }

                if (jsonParser.currentToken() == JsonToken.FIELD_NAME && !insideNestedObject) {
                    String fieldName = jsonParser.getCurrentName();
                    switch (fieldName) {
                        case String s when "id".equals(s) -> {
                            jsonParser.nextToken();
                            pokemonDTO.id(jsonParser.getValueAsInt());}
                        case String s when "name".equals(s) -> {
                            jsonParser.nextToken();
                            pokemonDTO.name(jsonParser.getValueAsString());}
                        case String s when "height".equals(s) -> {
                            jsonParser.nextToken();
                            pokemonDTO.height(jsonParser.getValueAsInt());}
                        case String s when "weight".equals(s) -> {
                            jsonParser.nextToken();
                            pokemonDTO.weight(jsonParser.getValueAsInt());}
                        case String s when "base_experience".equals(s) -> {
                            jsonParser.nextToken();
                            pokemonDTO.baseExperience(jsonParser.getValueAsInt());}
                        default -> {
                        }
                    }
                }
            }
        }

        return pokemonDTO.build();
    }
}
