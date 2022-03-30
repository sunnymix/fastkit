package net.restkit.core.common.data;

/**
 * @author sunnymix
 */
public enum Gender {
    MALE("MALE", "Male"),
    FEMALE("FEMALE", "Female");

    private final String value;
    private final String name;

    Gender(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
