/**
 * 
 */
package com.study.springhibernate.models;

import java.time.LocalDate;

/**
 * @author Admin
 *
 */
public class PassportDto {
	private Long id;
	private String number;
	private LocalDate issuedOn;
	private LocalDate validTill;
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
	public LocalDate getIssuedOn() {
		return issuedOn;
	}
	public void setIssuedOn(LocalDate issuedOn) {
		this.issuedOn = issuedOn;
	}
	public LocalDate getValidTill() {
		return validTill;
	}
	public void setValidTill(LocalDate validTill) {
		this.validTill = validTill;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	@Override
	public String toString() {
		return "PassportDto [id=" + id + ", number=" + number + ", issuedOn=" + issuedOn + ", validTill=" + validTill
				+ ", personId=" + personId + "]";
	}
}
