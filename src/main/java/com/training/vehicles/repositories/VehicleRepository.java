package com.training.vehicles.repositories;

import com.training.vehicles.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    Iterable<Vehicle> findAllByColor(String color);
}
