package com.study.springhibernate.service;

import com.study.springhibernate.entities.Vehicle;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @PersistenceContext
    private EntityManager em;

    /*public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = em.createQuery("" +
                "Select v " +
                "from Vehicle v", Vehicle.class)
                .getResultList();

        return vehicles;
    }*/

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = em.createQuery("" +
                "Select v " +
                "from Vehicle v " +
                "join fetch v.person p", Vehicle.class)
                .getResultList();

        return vehicles;
    }
}
