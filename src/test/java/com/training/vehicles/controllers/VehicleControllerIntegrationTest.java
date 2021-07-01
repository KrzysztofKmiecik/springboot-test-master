package com.training.vehicles.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.vehicles.entities.Vehicle;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class VehicleControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Flyway flyway;

    @Test
    public void shouldGetAllVehicles() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/vehicles"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        Vehicle[] vehicles = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Vehicle[].class);

        Assertions.assertEquals("Alfa Romeo", vehicles[0].getBrand());
    }

    @Test
    public void shouldReturn404WhenGet() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/vehicles/6"))
                .andExpect(MockMvcResultMatchers.status().is(404)).andReturn();

        String actualMessage = mvcResult.getResolvedException().getMessage();

        Assertions.assertEquals("No vehicle with such id: 6", actualMessage);
    }

    @Test
    public void shouldReturnCorrectValue() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/vehicles/1"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

    }


    @Test
    public void shouldAddNewElement() throws Exception {

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(6L);
        vehicle1.setModel("model1");
        vehicle1.setBrand("brand1");
        vehicle1.setColor("red");

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(vehicle1))
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
    }

    @AfterEach
    public void get() {
        flyway.clean();
        flyway.migrate();
    }

}