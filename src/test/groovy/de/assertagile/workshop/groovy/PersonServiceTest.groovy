package de.assertagile.workshop.groovy

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import spock.lang.Specification

import java.time.LocalDate
import java.util.Optional
import java.util.Set
import java.util.function.Predicate
import java.util.regex.Pattern

import static org.junit.jupiter.api.Assertions.*

class PersonServiceTest extends Specification {

    private static final Person TODD = new Person("Todd Ler", LocalDate.now().minusDays(300))
    private static final Person TINA = new Person("Tina Acher", LocalDate.now().minusYears(18).plusDays(1), "tina.acher@assertagile.de")
    private static final Person BEAR = new Person("Bear Lee Grownup", LocalDate.now().minusYears(18), "bear.lee.grownup@assertagile.de")
    private static final Person ANDREA = new Person("Andrea Aged", LocalDate.now().minusYears(59).plusDays(100))

    @Test
    @DisplayName("getAllPersons returns empty list initially")
    void getAllPersons1() {
        given:
        PersonService service = new PersonService()

        expect:
        Set.of() == service.getAllPersons()
    }

    @Test
    @DisplayName("getAllPersons returns all persons added")
    void getAllPersons2() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        Set.of(TODD, TINA, BEAR, ANDREA) == service.getAllPersons()
    }

    @Test
    @DisplayName("findPerson returns the named person")
    void findPerson1() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        Optional.of(TODD) == service.findPerson(TODD.getName())
    }

    @Test
    @DisplayName("findPerson an empty optional if name is unknown")
    void findPerson2() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        assertTrue(service.findPerson("Unknown").isEmpty())
    }

    @Test
    @DisplayName("findPersons returns all persons matching the given pattern")
    void findPersonsPattern1() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        // See regex here: https://regex101.com/r/9Y9XNd/1
        Set.of(TINA, TODD)== service.findPersons(Pattern.compile('^T.*$'))
    }

    @Test
    @DisplayName("findPersons returns all persons with the given birthday")
    void findPersonsLocalDate1() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        Set.of(ANDREA) == service.findPersons(ANDREA.getBirthday())
    }

    @Test
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


    @Test
    @DisplayName("findPersons returns fulfilling the given predicate")
    void findPersonsByPredicate() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD, TINA, BEAR, ANDREA)

        expect:
        Set.of(BEAR) == service.findPersons(person -> person.getName().length() > 11)
    }

    @Test
    @DisplayName("addPerson does not add duplicates")
    void addPerson() {
        given:
        PersonService service = new PersonService()
        service.addPersons(TODD)

        when:
        service.addPersons(new Person(TODD.getName(), TODD.getBirthday()))

        then:
        Set.of(TODD) == service.getAllPersons()
    }
}