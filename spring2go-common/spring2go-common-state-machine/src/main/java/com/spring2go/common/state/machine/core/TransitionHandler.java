package com.spring2go.common.state.machine.core;

/**
 * TODO
 *
 * @author xiaobin
 */
public class TransitionHandler<E extends Event> implements Transition {
    private String name;
    private State sourceState;
    private State targetState;
    private Class<E> eventType;
    private EventHandler<E> eventHandler;

    public TransitionHandler() {
        name = "DEFAULT_TRANSITION_NAME";
    }

    @Override
    public State getSourceState() {
        return sourceState;
    }

    public void setSourceState(State sourceState) {
        this.sourceState = sourceState;
    }

    @Override
    public State getTargetState() {
        return targetState;
    }

    public void setTargetState(State targetState) {
        this.targetState = targetState;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Class<E> getEventType() {
        return eventType;
    }

    public void setEventType(Class<E> eventType) {
        this.eventType = eventType;
    }

    @Override
    public EventHandler<? extends Event> getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler<E> eventHandler) {
        this.eventHandler = eventHandler;
    }
}
