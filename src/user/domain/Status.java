package user.domain;

import java.util.Arrays;

public enum Status {

    REQ("ST00"), NORMAL("ST01"), QUIT("ST02");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Status of (String value) {
        return Arrays.stream(values())
                .filter(s -> s.value().equals(value)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 상태가 존재하지 않습니다."));
    }
}
