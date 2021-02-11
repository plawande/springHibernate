/**
 * 
 */
package com.study.springhibernate.models;

/**
 * @author Admin
 *
 */
public class ProfileDto {
	private Long id;
	private String city;
	private String phoneNo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public String toString() {
		return "ProfileDto [id=" + id + ", city=" + city + ", phoneNo=" + phoneNo + "]";
	}
}
