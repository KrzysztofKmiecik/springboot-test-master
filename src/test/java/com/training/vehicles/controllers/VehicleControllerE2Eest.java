package com.training.vehicles.controllers;

import com.training.vehicles.entities.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class VehicleControllerE2Eest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void shouldGetSelectedColor() {
        ResponseEntity<Vehicle[]> actual = testRestTemplate.exchange("http://localhost:" + port + "/api/vehicles/filter?color=yellow",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Vehicle[].class
        );

        Assertions.assertEquals("Fiat", actual.getBody()[0].getBrand());
    }

}