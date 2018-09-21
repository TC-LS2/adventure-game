package com.drpicox.game.command;

public class IllegalCommandException extends RuntimeException {

    private String code;

    public IllegalCommandException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
