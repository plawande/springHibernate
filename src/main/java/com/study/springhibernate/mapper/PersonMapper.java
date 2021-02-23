/**
 * 
 */
package com.study.springhibernate.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.study.springhibernate.entities.*;
import com.study.springhibernate.models.*;
import org.springframework.stereotype.Component;

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
			for(Vehicle vehicle: vehicles) { //get all vehicles query is fired here (one-to-many)  if OSIV is not disabled in Spring Boot project.
				VehicleDto vehicleDto = new VehicleDto();
				vehicleDto.setId(vehicle.getId());
				vehicleDto.setNumber(vehicle.getNumber());
				vehicleDto.setType(vehicle.getType());
				vehicleDto.setPersonId(person.getId());
				vehicleDtos.add(vehicleDto);
			}
			personDto.setVehicleDtos(vehicleDtos);
		}

		List<Address> addresses = person.getAddresses();
		Optional<List<Address>> addressOptional = Optional.ofNullable(addresses);
		List<AddressDto> addressDtos = new ArrayList<>();
		if(addressOptional.isPresent()) {
			for(Address address: addresses) {
				AddressDto addressDto = new AddressDto();
				addressDto.setId(address.getId());
				addressDto.setHouseNo(address.getHouseNo());
				addressDto.setLine1(address.getLine1());
				addressDto.setLine2(address.getLine2());
				addressDto.setPersonId(person.getId());
				addressDtos.add(addressDto);
			}
			personDto.setAddressDtos(addressDtos);
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
