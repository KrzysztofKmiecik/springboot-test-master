package com.training.vehicles.services;

import com.training.vehicles.entities.Vehicle;
import com.training.vehicles.repositories.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    private VehicleService vehicleService;

    @BeforeEach
    private void setupRepository() {
        //given
        VehicleRepository vehicleRepository = mock(VehicleRepository.class);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setModel("model1");
        vehicle1.setBrand("brand1");
        vehicle1.setColor("red");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setModel("model2");
        vehicle2.setBrand("brand2");
        vehicle2.setColor("blue");
        Iterable<Vehicle> all = Arrays.asList(vehicle1, vehicle2);

        doReturn(all).when(vehicleRepository).findAll();
        vehicleService = new VehicleServiceImpl(vehicleRepository);
    }

    @Test
    @DisplayName("Should Return Selected Color")
    public void shouldReturnSelectedColor() {


        //when
        List<Vehicle> actual = vehicleService.findAllVehiclesByColor("red");
        //then
        Assertions.assertEquals(1L, actual.get(0).getId());
        Assertions.assertEquals("model1", actual.get(0).getModel());
        Assertions.assertEquals("brand1", actual.get(0).getBrand());
        Assertions.assertEquals("red", actual.get(0).getColor());
    }



    @Test
    //  @DisplayName("Should Return Selected Color")
    public void shouldNotReturnSelectedColor() {
        //when
        List<Vehicle> actual = vehicleService.findAllVehiclesByColor("black");
        //then
        Assertions.assertTrue(actual.isEmpty());
    }

}