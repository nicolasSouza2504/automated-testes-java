package br.com.tdd.service;

import br.com.tdd.model.Person;

public class PersonService implements IPersonService {

    @Override
    public Person createPerson(Person person) {

        if (person.getEmail() == null || person.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        return new Person(person.getFirstName(), person.getLastName(), person.getAddress(), person.getGender(), person.getEmail());

    }

}
