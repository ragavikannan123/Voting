package Common;

public enum Role {
    ADMIN("Male"),
    CANDIDATE("Female"),
    VOTER("NB");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
