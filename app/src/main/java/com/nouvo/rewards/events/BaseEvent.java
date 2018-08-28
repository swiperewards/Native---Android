package com.nouvo.rewards.events;

public class BaseEvent {

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int code) {
        this.status = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}