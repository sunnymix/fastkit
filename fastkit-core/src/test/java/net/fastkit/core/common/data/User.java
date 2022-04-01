package net.fastkit.core.common.data;

import java.util.Objects;

/**
 * @author sunnymix
 */
public class User {
    private String name;
    private Gender gender;
    private Grade grade;

    public User() {
    }

    public User(String name, Gender gender, Grade grade) {
        this.name = name;
        this.gender = gender;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && gender == user.gender && grade == user.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, grade);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", grade=" + grade +
                '}';
    }
}
