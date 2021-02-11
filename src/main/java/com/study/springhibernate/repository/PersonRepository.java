/**
 * 
 */
package com.study.springhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.springhibernate.entities.Person;

/**
 * @author Admin
 *
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
	public Person findByIdAndName(Long id, String name);
}
