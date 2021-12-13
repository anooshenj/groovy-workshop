package de.assertagile.workshop.groovy

import java.time.LocalDate
import java.util.Objects

class Person {

    private final String name
    private final LocalDate birthday
    private String emailAddress

    Person(String name, LocalDate birthday, String emailAddress) {
        this(name, birthday)
        this.emailAddress = emailAddress
    }

    Person(String name, LocalDate birthday) {
        this.name = name
        this.birthday = birthday
    }

    String getName() {
        return name
    }

    LocalDate getBirthday() {
        return birthday
    }

    String getEmailAddress() {
        return emailAddress
    }

    void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress
    }

    boolean isOfAge() {
        return !LocalDate.now().minusYears(18).isBefore(birthday)
    }

    @Override
    boolean equals(Object o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.getClass()) return false
        Person that = (Person) o
        return name == that.name &&
                birthday == that.birthday &&
                emailAddress == that.emailAddress
    }

    @Override
    int hashCode() {
        return Objects.hash(name, birthday, emailAddress)
    }

    @Override
    String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", emailAddress='" + emailAddress + '\'' +
                '}'
    }
}
