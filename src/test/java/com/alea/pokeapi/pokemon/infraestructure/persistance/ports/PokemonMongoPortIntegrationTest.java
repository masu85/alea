package com.alea.pokeapi.pokemon.infraestructure.persistance.ports;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.PokemonMongoDBO;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.mappers.PokemonMongoMapper;
import com.alea.pokeapi.pokemon.infraestructure.persistance.adapters.PokemonMongoAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("DBTest")
@SpringBootTest
class PokemonMongoPortIntegrationTest {

    @Autowired
    private PokemonMongoAdapter pokemonMongoAdapter;

    @MockBean
    private PokemonMongoMapper pokemonMongoMapper;

    private PokemonMongoPort pokemonMongoPort;


    @BeforeEach
    void setUp() {
        pokemonMongoPort = new PokemonMongoPort(pokemonMongoMapper, pokemonMongoAdapter);
    }

    @AfterEach
    void afterEach() {
        PokemonMongoDBO pokemonMongoDBO = PokemonMongoDBO.builder().id(-1).name("testing").height(7).weight(69).baseExperience(64).build();
        pokemonMongoAdapter.delete(pokemonMongoDBO).block();
    }

    @Test
    void createPokemon_Success() {
        ArgumentCaptor<PokemonMongoDBO> pokemongMongoDBOCaptor = ArgumentCaptor.forClass(PokemonMongoDBO.class);

        Pokemon pokemon = Pokemon.builder().id(-1).name("testing").height(7).weight(69).baseExperience(64).build();
        PokemonMongoDBO pokemonMongoDBO = PokemonMongoDBO.builder().id(-1).name("testing").height(7).weight(69).baseExperience(64).build();

        when(pokemonMongoMapper.pokemonToDataBase(pokemon)).thenReturn(pokemonMongoDBO);
        when(pokemonMongoMapper.dataBaseToPokemon(any())).thenReturn(pokemon);

        pokemonMongoPort.createPokemon(pokemon).block();

        verify(pokemonMongoMapper).dataBaseToPokemon(pokemongMongoDBOCaptor.capture());
        assertThat(pokemongMongoDBOCaptor).isNotNull();
        assertThat(pokemongMongoDBOCaptor.getValue().getId()).isEqualTo(pokemonMongoDBO.getId());
        assertThat(pokemongMongoDBOCaptor.getValue().getName()).isEqualTo(pokemonMongoDBO.getName());
        assertThat(pokemongMongoDBOCaptor.getValue().getWeight()).isEqualTo(pokemonMongoDBO.getWeight());
        assertThat(pokemongMongoDBOCaptor.getValue().getHeight()).isEqualTo(pokemonMongoDBO.getHeight());
        assertThat(pokemongMongoDBOCaptor.getValue().getBaseExperience()).isEqualTo(pokemonMongoDBO.getBaseExperience());
    }

    @Test
    void getAll_Success() {
        ArgumentCaptor<PokemonMongoDBO> pokemongMongoDBOCaptor = ArgumentCaptor.forClass(PokemonMongoDBO.class);

        Pokemon pokemon = Pokemon.builder().id(-1).name("testing").height(7).weight(69).baseExperience(64).build();
        PokemonMongoDBO pokemonMongoDBO = PokemonMongoDBO.builder().id(-1).name("testing").height(7).weight(69).baseExperience(64).build();

        pokemonMongoAdapter.deleteAll().block();
        pokemonMongoAdapter.save(pokemonMongoDBO).block();

        when(pokemonMongoMapper.pokemonToDataBase(pokemon)).thenReturn(pokemonMongoDBO);
        when(pokemonMongoMapper.dataBaseToPokemon(any())).thenReturn(pokemon);

        pokemonMongoPort.getAll().blockLast();

        verify(pokemonMongoMapper).dataBaseToPokemon(pokemongMongoDBOCaptor.capture());
        assertThat(pokemongMongoDBOCaptor).isNotNull();
        assertThat(pokemongMongoDBOCaptor.getValue().getId()).isEqualTo(pokemonMongoDBO.getId());
        assertThat(pokemongMongoDBOCaptor.getValue().getName()).isEqualTo(pokemonMongoDBO.getName());
        assertThat(pokemongMongoDBOCaptor.getValue().getWeight()).isEqualTo(pokemonMongoDBO.getWeight());
        assertThat(pokemongMongoDBOCaptor.getValue().getHeight()).isEqualTo(pokemonMongoDBO.getHeight());
        assertThat(pokemongMongoDBOCaptor.getValue().getBaseExperience()).isEqualTo(pokemonMongoDBO.getBaseExperience());
    }
}