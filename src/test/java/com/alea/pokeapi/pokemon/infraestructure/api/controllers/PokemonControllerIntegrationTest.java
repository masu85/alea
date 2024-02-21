package com.alea.pokeapi.pokemon.infraestructure.api.controllers;

import com.alea.pokeapi.pokemon.application.GetHeaviestPokemonsUseCase;
import com.alea.pokeapi.pokemon.application.GetHighestPokemonUseCase;
import com.alea.pokeapi.pokemon.application.GetMoreBaseExperiencePokemonUseCase;
import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.HeaviestPokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.HighestPokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.MoreBaseExperiencePokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.mappers.PokemonAPIMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.net.URI;

import static org.mockito.Mockito.when;

@WebFluxTest
class PokemonControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GetHeaviestPokemonsUseCase getHeaviestPokemonsUseCase;
    @MockBean
    private GetHighestPokemonUseCase getHighestPokemonUseCase;
    @MockBean
    private GetMoreBaseExperiencePokemonUseCase getMoreBaseExperiencePokemonUseCase;
    @MockBean
    private PokemonAPIMapper pokemonAPIMapper;

    @Test
    public void getHeaviestEndpoint_ok_200() {

        var pk1 = Pokemon.builder().id(1).name("a").weight(1).build();
        var ph1 = HeaviestPokemonDTO.builder().id(1).name("a").weight(1).build();

        when(getHeaviestPokemonsUseCase.getHeaviestPokemon()).thenReturn(Flux.just(pk1));
        when(pokemonAPIMapper.pokemonToHeaviestPokemon(pk1)).thenReturn(ph1);

        webTestClient.get()
                .uri(URI.create("/pokemon/heaviest"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$.[0].name").isEqualTo(ph1.getName())
                .jsonPath("$.[0].weight").isEqualTo(ph1.getWeight());
    }

    @Test
    public void getHeaviestEndpoint_throws_exception() {

        when(getHeaviestPokemonsUseCase.getHeaviestPokemon()).thenReturn(Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected API error")));

        webTestClient.get()
                .uri(URI.create("/pokemon/heaviest"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getHighestEndpoint_ok_200() {

        var pk1 = Pokemon.builder().id(1).name("a").height(1).build();
        var ph1 = HighestPokemonDTO.builder().id(1).name("a").height(1).build();

        when(getHighestPokemonUseCase.getHighestPokemon()).thenReturn(Flux.just(pk1));
        when(pokemonAPIMapper.pokemonToHighestPokemon(pk1)).thenReturn(ph1);

        webTestClient.get()
                .uri(URI.create("/pokemon/highest"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$.[0].name").isEqualTo(ph1.getName())
                .jsonPath("$.[0].height").isEqualTo(ph1.getHeight());
    }

    @Test
    public void getHighestEndpoint_throws_exception() {

        when(getHighestPokemonUseCase.getHighestPokemon()).thenReturn(Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected API error")));

        webTestClient.get()
                .uri(URI.create("/pokemon/highest"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getMoreBaseExperienceEndpoint_ok_200() {

        var pk1 = Pokemon.builder().id(1).name("a").baseExperience(1).build();
        var ph1 = MoreBaseExperiencePokemonDTO.builder().id(1).name("a").baseExperience(1).build();

        when(getMoreBaseExperiencePokemonUseCase.getMoreBaseExperiencePokemon()).thenReturn(Flux.just(pk1));
        when(pokemonAPIMapper.pokemonToMoreBaseExperiencePokemon(pk1)).thenReturn(ph1);

        webTestClient.get()
                .uri(URI.create("/pokemon/experience"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$.[0].name").isEqualTo(ph1.getName())
                .jsonPath("$.[0].baseExperience").isEqualTo(ph1.getBaseExperience());
    }

    @Test
    public void getMoreBaseExperienceEndpoint_throws_exception() {

        when(getMoreBaseExperiencePokemonUseCase.getMoreBaseExperiencePokemon()).thenReturn(Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected API error")));

        webTestClient.get()
                .uri(URI.create("/pokemon/experience"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}