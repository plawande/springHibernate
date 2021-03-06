/**
 * 
 */
package com.study.springhibernate.entities;

import javax.persistence.*;

/**
 * @author Admin
 *
 */
@Entity
@Table(name="person_profile")
public class Profile {
	@Id
	@Column(name="person_id")
	private Long id;
	
	@Column(name="city")
	private String city;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@OneToOne(fetch=FetchType.LAZY)
	@MapsId
	private Person person;
	
	public Profile() {
	}
	
	public Profile(Long id, String city, String phoneNo, Person person) {
		this.id = id;
		this.city = city;
		this.phoneNo = phoneNo;
		this.person = person;
	}
	
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", city=" + city + ", phoneNo=" + phoneNo + "]";
	}
}

/*@Entity
@Table(name="person_profile")
public class Profile {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="city")
	private String city;

	@Column(name="phone_no")
	private String phoneNo;

	@OneToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person person;

	public Profile() {
	}

	public Profile(Long id, String city, String phoneNo, Person person) {
		this.id = id;
		this.city = city;
		this.phoneNo = phoneNo;
		this.person = person;
	}

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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", city=" + city + ", phoneNo=" + phoneNo + "]";
	}
}*/
