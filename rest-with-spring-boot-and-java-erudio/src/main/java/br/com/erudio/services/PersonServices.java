package br.com.erudio.services;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository personRepository;

	public List<Person> findAll() {

		logger.info("Finding all people!");

		return personRepository.findAll();
	}

	public Person findById(Long id) {
		
		logger.info("Finding one person!");
		
		return personRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	}
	
	public Person create(Person person) {

		logger.info("Creating one person!");

		Optional<Person> personAlreadySaved = personRepository.findByEmail(person.getEmail());

		personAlreadySaved.ifPresent((persons) -> {
			throw new RuntimeException("User Already exists");
        });

		return personRepository.save(person);

	}
	
	public Person update(Person person) {
		
		logger.info("Updating one person!");
		
		var entity = personRepository.findById(person.getId())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return personRepository.save(person);
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one person!");
		
		var entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		personRepository.delete(entity);

	}

}
