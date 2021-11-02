package com.spring2go.common.state.machine.core;

/**
 * 事件
 *
 * @author xiaobin
 */
public interface Event {
    /**
     * Name of the event.
     *
     * @return event name
     */
    String getName();

    /**
     * Timestamp of the event.
     *
     * @return event timestamp
     */
    long getTimestamp();
}
