/**
 * 
 */
package com.study.springhibernate.controller;

import com.study.springhibernate.entities.Vehicle;
import com.study.springhibernate.mapper.VehicleMapper;
import com.study.springhibernate.models.VehicleDto;
import com.study.springhibernate.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.springhibernate.entities.Person;
import com.study.springhibernate.mapper.PersonMapper;
import com.study.springhibernate.models.PersonDto;
import com.study.springhibernate.service.PersonService;

import java.util.List;

/**
 * @author Admin
 *
 */
@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;

	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private PersonMapper personMapper;

	@Autowired
	private VehicleMapper vehicleMapper;
	
	@GetMapping(path = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonDto getPersonById(@PathVariable Long id) {
		Person person = personService.getPersonById(id);
		PersonDto personDto = personMapper.getPersonDto(person);
		return personDto;
	}
	
	@GetMapping(path = "/person/{id}/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonDto getPersonByIdAndName(@PathVariable Long id, @PathVariable String name) {
		Person person = personService.getPersonByIdAndName(id, name);
		PersonDto personDto = personMapper.getPersonDto(person);
		return personDto;
	}
	
	@PostMapping(path = "/person/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonDto savePerson(@RequestBody PersonDto personDto) {	
		Person person = personMapper.getPersonEntity(personDto);
		Person savedPerson = personService.savePerson(person);
		return personMapper.getPersonDto(savedPerson);
	}
	
	@PutMapping(path = "/person/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonDto updatePerson(@RequestBody PersonDto personDto) {	
		Person person = personMapper.getPersonEntity(personDto);
		Person savedPerson = personService.savePerson(person);
		return personMapper.getPersonDto(savedPerson);
	}

	@GetMapping(path = "/vehicle", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<VehicleDto> getAllVehicles() {
		List<Vehicle> vehicles = vehicleService.getAllVehicles();
		List<VehicleDto> allVehicles = vehicleMapper.getAllVehicles(vehicles);
		return allVehicles;
	}
}
