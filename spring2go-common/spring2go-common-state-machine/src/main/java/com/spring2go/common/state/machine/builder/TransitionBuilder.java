package com.spring2go.common.state.machine.builder;

import com.spring2go.common.state.machine.core.*;

/**
 * 转换构建器
 *
 * @author xiaobin
 */
public class TransitionBuilder {
    private final TransitionHandler transition;

    /**
     * Create a new {@link TransitionBuilder}.
     */
    public TransitionBuilder() {
        transition = new TransitionHandler();
    }

    /**
     * Set the name of the transition.
     *
     * @param name of the transition
     * @return FSM transition builder
     */
    public TransitionBuilder name(final String name) {
        transition.setName(name);
        return this;
    }

    /**
     * Set the source state of the transition.
     *
     * @param sourceState of the transition
     * @return FSM transition builder
     */
    public TransitionBuilder sourceState(final State sourceState) {
        transition.setSourceState(sourceState);
        return this;
    }

    /**
     * Set the target state of the transition.
     *
     * @param targetState of the transition
     * @return FSM transition builder
     */
    public TransitionBuilder targetState(final State targetState) {
        transition.setTargetState(targetState);
        return this;
    }

    /**
     * Set event type upon which the transition should be triggered.
     *
     * @param eventType of the transition
     * @return FSM transition builder
     */
    public TransitionBuilder eventType(final Class<? extends Event> eventType) {
        transition.setEventType(eventType);
        return this;
    }

    /**
     * Set the event handler of the transition.
     *
     * @param eventHandler of the transition
     * @return FSM transition builder
     */
    public <E extends Event> TransitionBuilder eventHandler(final EventHandler<E> eventHandler) {
        transition.setEventHandler(eventHandler);
        return this;
    }

    /**
     * Build a transition instance.
     *
     * @return a transition instance.
     */
    public Transition build() {
        return transition;
    }
}
