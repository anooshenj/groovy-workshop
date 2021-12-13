package de.assertagile.workshop.groovy

import java.time.LocalDate
import java.util.*
import java.util.function.Predicate
import java.util.regex.Pattern

import static java.util.stream.Collectors.toSet

class PersonService {

    private final Set<Person> persons = new HashSet<>()

    Collection<Person> getAllPersons() {
        return persons
    }

    Optional<Person> findPerson(String name) {
        return persons.stream()
                .filter(person -> person.getName() == name)
                .findFirst()
    }

    Collection<Person> findPersons(LocalDate birthday) {
        return persons.stream()
                .filter(person -> person.getBirthday() == birthday)
                .collect(toSet())
    }

    Collection<Person> findPersons(Pattern regex) {
        return persons.stream()
                .filter(person -> regex.matcher(person.getName()).matches())
                .collect(toSet())
    }

    Collection<Person> findPersons(Predicate<? super Person> predicate) {
        return persons.stream().filter(predicate).collect(toSet())
    }

    Collection<Person> findPersons(Object criteria) {
        throw new IllegalArgumentException("This is not implemented, yet!")
    }

    void addPersons(Person... persons) {
        this.persons.addAll(Arrays.asList(persons))
    }
}
