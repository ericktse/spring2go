package com.spring2go.common.state.machine.core;

import java.util.Date;

/**
 * @author xiaobin
 * @description 抽象事件基类
 * @date 2021/11/1
 */
public abstract class AbstractEvent implements Event {
    private static final String DEFAULT_EVENT_NAME = "Event";


    protected String name;
    protected long timestamp;

    protected AbstractEvent() {
        this.name = DEFAULT_EVENT_NAME;
        timestamp = System.currentTimeMillis();
    }

    protected AbstractEvent(final String name) {
        this.name = name;
        timestamp = System.currentTimeMillis();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Event" +
                "{name='" + name + '\'' +
                ", timestamp=" + new Date(timestamp) +
                '}';
    }

}
