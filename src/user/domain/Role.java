package user.domain;

import java.util.Arrays;

public enum Role {

    MEMBER("10"), ADMIN("20");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Role of(String value) {
        return Arrays.stream(values())
                .filter(role -> role.value().equals(value)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 ROLE을 찾을 수 없습니다."));
    }
}
