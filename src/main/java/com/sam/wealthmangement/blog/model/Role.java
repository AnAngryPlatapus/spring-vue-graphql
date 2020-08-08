package com.sam.wealthmangement.blog.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("user"),
    MODERATOR("mod"),
    ADMIN("admin"),
    UNDEFINED("undefined");

    private String displayName;

    public static Role of(String name) {
        return Arrays.stream(Role.values()).filter(r -> r.name().equals(name))
                .findFirst()
                .orElse(Role.UNDEFINED);
    }
}
