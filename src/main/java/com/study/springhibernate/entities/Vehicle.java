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
@Table(name="person_vehicle")
public class Vehicle {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="number")
	private String number;
	
	@Column(name="type")
	private String type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person_id")
	private Person person;

	public Vehicle() {
	}

	public Vehicle(Long id, String number, String type, Person person) {
		this.id = id;
		this.number = number;
		this.type = type;
		this.person = person;
	}

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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", number=" + number + ", type=" + type + "]";
	}
}
