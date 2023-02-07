package com.openrum.collector.processor.enums;

public enum EnumEventType {
    APPLICATION("application"),
    VIEW("view"),
    USERACTION("userAction"),
    RESOURCE("resource"),
    EXCEPTION("exception"),
    LONGTASK("longTask"),
    SYSTEM("system"),
    CUSTOM("custom");

    private final String type;

    EnumEventType(String type) {
        this.type = type;
    }

    public String getEventType() {
        return type;
    }

}
