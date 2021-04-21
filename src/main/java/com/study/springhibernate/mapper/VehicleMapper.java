package com.study.springhibernate.mapper;

import com.study.springhibernate.entities.Vehicle;
import com.study.springhibernate.models.VehicleDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleMapper {
    public List<VehicleDto> getAllVehicles(List<Vehicle> vehicles) {
        List<VehicleDto> vehicleDtos = new ArrayList<>();
        for(Vehicle veh: vehicles) {
            VehicleDto vehicleDto = getVehicleDto(veh);
            vehicleDtos.add(vehicleDto);
        }
        return vehicleDtos;
    }

    private VehicleDto getVehicleDto(Vehicle veh) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(veh.getId());
        vehicleDto.setNumber(veh.getNumber());
        vehicleDto.setType(veh.getType());
        vehicleDto.setPersonId(veh.getPerson().getId());
        return vehicleDto;
    }
}
