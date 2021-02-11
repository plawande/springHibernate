/**
 * 
 */
package com.study.springhibernate.models;

/**
 * @author Admin
 *
 */
public class VehicleDto {
	private Long id;
	private String number;
	private String type;
	private Long personId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	@Override
	public String toString() {
		return "VehicleDto [id=" + id + ", number=" + number + ", type=" + type + ", personId=" + personId + "]";
	}
}
