/**
 * 
 */
package com.study.springhibernate.models;

import java.util.List;

/**
 * @author Admin
 *
 */
public class PersonDto {
	private Long id;
	private String name;
	private PassportDto passportDto;
	private ProfileDto profileDto;
	private List<VehicleDto> vehicleDtos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PassportDto getPassportDto() {
		return passportDto;
	}
	public void setPassportDto(PassportDto passportDto) {
		this.passportDto = passportDto;
	}
	public ProfileDto getProfileDto() {
		return profileDto;
	}
	public void setProfileDto(ProfileDto profileDto) {
		this.profileDto = profileDto;
	}
	public List<VehicleDto> getVehicleDtos() {
		return vehicleDtos;
	}
	public void setVehicleDtos(List<VehicleDto> vehicleDtos) {
		this.vehicleDtos = vehicleDtos;
	}
	
	@Override
	public String toString() {
		return "PersonDto [id=" + id + ", name=" + name + ", passportDto=" + passportDto + ", profileDto=" + profileDto
				+ ", vehicleDtos=" + vehicleDtos + "]";
	}
}
