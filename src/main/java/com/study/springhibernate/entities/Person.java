/**
 * 
 */
package com.study.springhibernate.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Admin
 *
 */
@Entity
@Table(name="person_base")
public class Person {
	@Id
	//@GeneratedValue
	//@GeneratedValue(strategy=GenerationType.AUTO)  //Table 'springhibernatedb.hibernate_sequence' doesn't exist
	@GeneratedValue(strategy=GenerationType.IDENTITY)  //To use a MySQL AUTO_INCREMENT column, you are supposed to use an IDENTITY strategy
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@OneToOne(mappedBy="person", cascade=CascadeType.ALL) //, cascade=CascadeType.MERGE
	private Passport passport;
	
	@OneToOne(mappedBy="person", cascade=CascadeType.ALL)
	private Profile profile;
	
	@OneToMany(mappedBy="person", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	private List<Vehicle> vehicles;
	
	public Person() {
	}

	public Person(Long id, String name, Passport passport, Profile profile, List<Vehicle> vehicles) {
		this.id = id;
		this.name = name;
		this.passport = passport;
		this.profile = profile;
		this.vehicles = vehicles;
	}

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

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	/*@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", passport=" + passport + ", profile=" + profile + ", vehicles="
				+ vehicles + "]";
	}*/
}

//https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/ (read always when you do refresher)
//https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
//https://vladmihalcea.com/a-beginners-guide-to-jpa-and-hibernate-cascade-types/

/*Default (one-to-one -> Eager; one-to-many -> Lazy)::
	Hibernate: (query fired at service layer where the main get is)
	    select
	        person0_.id as id1_0_0_,
	        person0_.name as name2_0_0_,
	        passport1_.id as id1_1_1_,
	        passport1_.issued_on as issued_o2_1_1_,
	        passport1_.number as number3_1_1_,
	        passport1_.person_id as person_i5_1_1_,
	        passport1_.valid_till as valid_ti4_1_1_,
	        profile2_.id as id1_2_2_,
	        profile2_.city as city2_2_2_,
	        profile2_.phone_no as phone_no3_2_2_ 
	    from
	        person_base person0_ 
	    left outer join
	        person_passport passport1_ 
	            on person0_.id=passport1_.person_id 
	    left outer join
	        person_profile profile2_ 
	            on person0_.id=profile2_.id 
	    where
	        person0_.id=?
	Hibernate: (query fired wherever the required getter is)
	    select
	        vehicles0_.person_id as person_i4_3_0_,
	        vehicles0_.id as id1_3_0_,
	        vehicles0_.id as id1_3_1_,
	        vehicles0_.number as number2_3_1_,
	        vehicles0_.person_id as person_i4_3_1_,
	        vehicles0_.type as type3_3_1_ 
	    from
	        person_vehicle vehicles0_ 
	    where
	        vehicles0_.person_id=?*/



/*Custom (all lazy)::
	Hibernate: (query fired at service layer where the main get is)
	    select
	        person0_.id as id1_0_0_,
	        person0_.name as name2_0_0_ 
	    from
	        person_base person0_ 
	    where
	        person0_.id=?
	Hibernate: (query fired at service layer where the main get is)
	    select
	        passport0_.id as id1_1_0_,
	        passport0_.issued_on as issued_o2_1_0_,
	        passport0_.number as number3_1_0_,
	        passport0_.person_id as person_i5_1_0_,
	        passport0_.valid_till as valid_ti4_1_0_ 
	    from
	        person_passport passport0_ 
	    where
	        passport0_.person_id=?
	Hibernate: (query fired at service layer where the main get is)
	    select
	        profile0_.id as id1_2_0_,
	        profile0_.city as city2_2_0_,
	        profile0_.phone_no as phone_no3_2_0_ 
	    from
	        person_profile profile0_ 
	    where
	        profile0_.id=?
	Hibernate: (query fired wherever the required getter is)
	    select
	        vehicles0_.person_id as person_i4_3_0_,
	        vehicles0_.id as id1_3_0_,
	        vehicles0_.id as id1_3_1_,
	        vehicles0_.number as number2_3_1_,
	        vehicles0_.person_id as person_i4_3_1_,
	        vehicles0_.type as type3_3_1_ 
	    from
	        person_vehicle vehicles0_ 
	    where
	        vehicles0_.person_id=?*/

 

