package com.spring2go.common.state.machine.core;

/**
 * 事件处理器
 *
 * @author xiaobin
 */
public interface EventHandler<E extends Event> {

    /**
     * Action method to execute when an event occurs.
     *
     * @param event the triggered event
     * @throws Exception thrown if a problem occurs during action performing
     */
    void handle(E event) throws Exception;
}
