package com.spring2go.common.state.machine.core;

/**
 * 转换器
 *
 * @author xiaobin
 */
public interface Transition {
    /**
     * Return transition name.
     * @return transition name
     */
    String getName();

    /**
     * Return transition source state.
     * @return transition source state
     */
    State getSourceState();

    /**
     * Return transition target state.
     * @return transition target state
     */
    State getTargetState();

    /**
     * Return fired event type upon which the transition should be made.
     * @return Event type class
     */
    Class<? extends Event> getEventType();

    /**
     * Return event handler to execute when an event is fired.
     * @return transition event handler
     */
    EventHandler getEventHandler();
}
