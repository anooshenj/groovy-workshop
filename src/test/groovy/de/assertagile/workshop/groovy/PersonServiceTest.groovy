package de.assertagile.workshop.groovy

import spock.lang.Specification

import java.time.LocalDate
import java.util.regex.Pattern

class PersonServiceTest extends Specification {

    private static final Person TODD = new Person("Todd Ler", LocalDate.now().minusDays(300))
    private static final Person TINA = new Person("Tina Acher", LocalDate.now().minusYears(18).plusDays(1), "tina.acher@assertagile.de")
    private static final Person BEAR = new Person("Bear Lee Grownup", LocalDate.now().minusYears(18), "bear.lee.grownup@assertagile.de")
    private static final Person ANDREA = new Person("Andrea Aged", LocalDate.now().minusYears(59).plusDays(100))

    def "getAllPersons returns empty list initially"() {
        given:
        PersonService service = new PersonService()

        expect:
        Set.of() == service.getAllPersons()
    }


    def "getAllPersons returns all persons added"() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        Set.of(TODD, TINA, BEAR, ANDREA) == service.getAllPersons()
    }


    def "findPerson returns the named person"() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        Optional.of(TODD) == service.findPerson(TODD.getName())
    }


    def "findPerson an empty optional if name is unknown"() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        service.findPerson("Unknown").isEmpty()
    }


    def "findPersons returns all persons matching the given pattern"() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        // See regex here: https://regex101.com/r/9Y9XNd/1
        Set.of(TINA, TODD) == service.findPersons(Pattern.compile('^T.*$'))
    }


    def "findPersons returns all persons with the given birthday"() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        Set.of(ANDREA) == service.findPersons(ANDREA.getBirthday())
    }


    def "findPersons by object throws IllegalArgumentException"() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)
        Object regex = '^T.*$'

        when:
        service.findPersons(regex)

        then:
        thrown(IllegalArgumentException)
    }


    def "findPersons returns fulfilling the given predicate"() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        Set.of(BEAR) == service.findPersons(person -> person.getName().length() > 11)
    }


    def "addPerson does not add duplicates"() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD)

        when:
        service.addPersons(new Person(TODD.getName(), TODD.getBirthday()))

        then:
        Set.of(TODD) == service.getAllPersons()
    }
}