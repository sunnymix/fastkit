package net.restkit.core.common.data;

/**
 * @author sunnymix
 */
public enum Grade {
    ONE(1, "One"),
    TWO(2, "Two");

    private final Integer code;
    private final String name;

    Grade(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
