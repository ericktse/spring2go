package com.spring2go.common.state.machine.exception;

import com.spring2go.common.state.machine.core.Event;
import com.spring2go.common.state.machine.core.Transition;

/**
 * 状态机自定义异常
 *
 * @author xiaobin
 */
public class StateMachineException extends Exception {
    /**
     * The transition where the exception occurred.
     */
    private final Transition transition;

    /**
     * The event triggered when the exception occurred.
     */
    private final Event event;

    /**
     * The root cause of the exception.
     */
    private final Throwable cause;

    /**
     * Create a new {@link StateMachineException}.
     *
     * @param transition where the exception occurred
     * @param event      triggered when the exception occurred
     * @param cause      root cause of the exception
     */
    public StateMachineException(final Transition transition, final Event event, final Throwable cause) {
        this.transition = transition;
        this.event = event;
        this.cause = cause;
    }

    /**
     * Get the transition where the exception occurred.
     *
     * @return the transition where the exception occurred.
     */
    public Transition getTransition() {
        return transition;
    }

    /**
     * Get the event triggered when the exception occurred.
     *
     * @return the event triggered when the exception occurred.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Get the root cause of the exception.
     *
     * @return the root cause of the exception
     */
    @Override
    public Throwable getCause() {
        return cause;
    }
}
