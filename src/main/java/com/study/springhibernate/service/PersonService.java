/**
 * 
 */
package com.study.springhibernate.service;

import com.study.springhibernate.entities.Person;

/**
 * @author Admin
 *
 */
public interface PersonService {	
	Person getPersonById(Long id);
	Person getPersonByIdAndName(Long id, String name);
	Person savePerson(Person person);
}
