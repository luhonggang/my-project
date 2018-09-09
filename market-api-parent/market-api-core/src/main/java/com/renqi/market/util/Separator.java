package com.renqi.market.util;

/**
 * 枚举类
 */
public enum Separator {
    COMMA(","),
    COLON(":"),
    SEMICOLON(";"),
    UNDERLINE("\\|"),
    EQUALSIGN("="),
    RIGHT_BRACE("{"),
    LEFT_BRACE("}"),
    RIGHT_BRACKET("["),
    LEFT_BRACKET("]"),
    RIGHT_MIN_BRACE("("),
    LEFT_MIN_BRACE(")"),
    SPOT("."),
    DASH("-"),
    SPRIT("/");

    Separator(String type) {
        this.value = type;
    }

    private String value;
    public String getValue() {
        return value;
    }
}
