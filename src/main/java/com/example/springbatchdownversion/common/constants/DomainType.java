package com.example.springbatchdownversion.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DomainType {
    USER("user", "User Domain"),
    ;

    private final String key;
    private final String desc;
}
