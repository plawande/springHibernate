/**
 * 
 */
package com.study.springhibernate.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.springhibernate.repository.PersonRepository;
import com.study.springhibernate.entities.Person;

import java.util.Optional;

/**
 * @author Admin
 *
 */
@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepository personRepository;

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Person getPersonById(Long id) {
		// TODO Auto-generated method stub
		Optional<Person> personOptional = personRepository.findById(id); //basic and one-to-one mapping queries fired here
		Person person = null;
		if(personOptional.isPresent()) {
			person = personOptional.get();
		}
		return person;		
	}
	
	/*@Override
	@Transactional
	public Person getPersonById(Long id) {
		// TODO Auto-generated method stub
		Person person = em.find(Person.class, id);
		person.setName("Prajyot Great!");  //This will trigger an update iff @Transactional is present on top of this method
		return person;
	}*/

	@Override
	@Transactional(readOnly=true)
	public Person getPersonByIdAndName(Long id, String name) {
		// TODO Auto-generated method stub
		Person person = personRepository.findByIdAndName(id, name);
		return person;
	}
	
	
	
	/*
	 * The personRepository.save method already has a @Transactional on it.
	 * If it's a fresh insert(persist) of person then it would have all insert queries. 
	 * Else if it's an update(merge) then this method will first fire a fully eager select, do the dirty checking and then update each individual changed rows across tables.
	 * It may even insert if new rows added and provide proper pks, fks.
	 * If a child is dissociated from parent, it will delete a child from db if orphanRemoval=true and with proper cascading.
	 * The method below that shows how it would work if we had to use entityManager persist & merge methods.
	 */	
	 @Override 
	 public Person savePerson(Person person) { 
		 Person savedPerson = personRepository.save(person); 
		 return savedPerson; 
	 }
	
	/*@Override
	@Transactional
	public Person savePerson(Person person) {
		// TODO Auto-generated method stub
		if(person.getId() == null)
			em.persist(person);
		else
			return em.merge(person);
		return person;
	}*/
	 
}


//https://www.baeldung.com/jpa-hibernate-persistence-context
//https://thorben-janssen.com/transactions-spring-data-jpa/
//https://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge
//https://stackoverflow.com/questions/5640778/hibernate-sessionfactory-vs-jpa-entitymanagerfactory


//The SimpleJpaRepository which an implementation of JpaRepository has @Transactional(readOnly = true) on top.
//All save methods inside it have @Transactional