package com.example.lanhuajian.customeventbus.eventbus;

public class CustomEventBusBean {

    private String tag;

    private String message;

    public CustomEventBusBean(String tag, String message) {
        this.tag = tag;
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomEventBusBean{" +
                "tag='" + tag + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
