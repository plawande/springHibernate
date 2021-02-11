package com.study.springhibernate.repository;

import com.study.springhibernate.entities.Person;
import com.study.springhibernate.entities.Vehicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testFindPersonById() throws InterruptedException {
        Optional<Person> personOptional = personRepository.findById(Long.valueOf(14));
        Person person = personOptional.get();
        List<Vehicle> vehicles = person.getVehicles();
        //Thread.sleep(10000);
        System.out.println("Vehicle queries: -----------------------------------------------------");
        System.out.println(vehicles);
    }
}

//https://stackoverflow.com/questions/49894587/lazyinitializationexception-could-not-initialize-proxy-no-session
