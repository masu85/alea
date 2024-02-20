package com.alea.pokeapi.pokemon.infraestructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class UpdateLockConfiguration {

    @Bean
    public Lock updateLock() {
        return new ReentrantLock();
    }
}
