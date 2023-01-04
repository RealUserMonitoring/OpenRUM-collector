package com.openrum.collector.processor.enums;


/**
 * @author zhaoc
 */
public enum TimingDataEnum {

    DOM_LOADING("dom_loading"),
    DOM_INTERACTIVE("dom_interactive"),
    VIEW_LOAD_TIMING_DATA("view.load.timing_data"),
    RESOURCE_TIMING_DATA("resource.timing_data");

    TimingDataEnum(String attribute) {
        this.attribute = attribute;
    }

    private final String attribute;

    public String getAttribute() {
        return attribute;
    }

}
