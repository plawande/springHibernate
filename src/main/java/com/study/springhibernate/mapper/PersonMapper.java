/**
 * 
 */
package com.study.springhibernate.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.study.springhibernate.entities.Passport;
import com.study.springhibernate.entities.Person;
import com.study.springhibernate.entities.Profile;
import com.study.springhibernate.entities.Vehicle;
import com.study.springhibernate.models.PassportDto;
import com.study.springhibernate.models.PersonDto;
import com.study.springhibernate.models.ProfileDto;
import com.study.springhibernate.models.VehicleDto;

/**
 * @author Admin
 *
 */
@Component
public class PersonMapper {
	public PersonDto getPersonDto(Person person) {
		PersonDto personDto = new PersonDto();
		personDto.setId(person.getId());
		personDto.setName(person.getName());
		
		Passport passport = person.getPassport();
		PassportDto passportDto = new PassportDto();
		passportDto.setId(passport.getId());
		passportDto.setNumber(passport.getNumber());
		passportDto.setIssuedOn(passport.getIssuedOn());
		passportDto.setValidTill(passport.getValidTill());
		passportDto.setPersonId(passport.getPerson().getId());
		personDto.setPassportDto(passportDto);
		
		Profile profile = person.getProfile();
		ProfileDto profileDto = new ProfileDto();
		profileDto.setId(profile.getId());
		profileDto.setCity(profile.getCity());
		profileDto.setPhoneNo(profile.getPhoneNo());
		personDto.setProfileDto(profileDto);
		
		List<Vehicle> vehicles = person.getVehicles();
		Optional<List<Vehicle>> vehicleOptional = Optional.ofNullable(vehicles);
		List<VehicleDto> vehicleDtos = new ArrayList<>();
		if(vehicleOptional.isPresent()) {
			for(Vehicle vehicle: vehicles) { //get all vehicles query is fired here (one-to-many)  //does hibernate need transactional for this?
				VehicleDto vehicleDto = new VehicleDto();
				vehicleDto.setId(vehicle.getId());
				vehicleDto.setNumber(vehicle.getNumber());
				vehicleDto.setType(vehicle.getType());
				vehicleDto.setPersonId(person.getId());
				vehicleDtos.add(vehicleDto);
			}
			personDto.setVehicleDtos(vehicleDtos);
		}
		
		return personDto;
	}
	
	public Person getPersonEntity(PersonDto personDto) {
		Person person = new Person();
		person.setId(personDto.getId());
		person.setName(personDto.getName());
		
		Passport passport = new Passport();
		PassportDto passportDto = personDto.getPassportDto();
		passport.setId(passportDto.getId());
		passport.setNumber(passportDto.getNumber());
		passport.setIssuedOn(passportDto.getIssuedOn());
		passport.setValidTill(passportDto.getValidTill());
		person.setPassport(passport);
		passport.setPerson(person);
		
		Profile profile = new Profile();
		ProfileDto profileDto = personDto.getProfileDto();
		profile.setId(profileDto.getId());
		profile.setCity(profileDto.getCity());
		profile.setPhoneNo(profileDto.getPhoneNo());
		person.setProfile(profile);
		profile.setPerson(person);
		
		List<Vehicle> vehicles = new ArrayList<>();
		List<VehicleDto> vehicleDtos = personDto.getVehicleDtos();
		if(vehicleDtos != null) {
			for(VehicleDto veh: vehicleDtos) {
				Vehicle vehicle = new Vehicle();
				vehicle.setId(veh.getId());
				vehicle.setNumber(veh.getNumber());
				vehicle.setType(veh.getType());
				vehicle.setPerson(person);
				vehicles.add(vehicle);
			}
			person.setVehicles(vehicles);
		}
		
		return person;
	}
}
