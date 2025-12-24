package com.example.springbatchdownversion.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SourceType {
    JPA("jpa", "Default JPA Source"),
    MYBATIS("mybatis", "Default JDBC Source"),
    EXCEL("excel", "Excel Source"),
    ;

    private final String key;
    private final String desc;
}
