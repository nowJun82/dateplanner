package com.dateplanner.util;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

public class initData {
    @Configuration
    @AllArgsConstructor
    @Profile("!prod")
    public class InitData {

        @Bean
        public ApplicationRunner init() {
            return args -> {

            };
        }
    }
}