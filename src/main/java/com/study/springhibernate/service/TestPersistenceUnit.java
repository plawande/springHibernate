package com.study.springhibernate.service;

import com.study.springhibernate.entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestPersistenceUnit {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
        EntityManager em = emf.createEntityManager();
        //em.getTransaction().begin();

        Person person = em.find(Person.class, Long.valueOf(14));
        System.out.println(person.getName());
        System.out.println(person.getPassport());
        System.out.println(person.getProfile());
        System.out.println(person.getVehicles());

        //em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
