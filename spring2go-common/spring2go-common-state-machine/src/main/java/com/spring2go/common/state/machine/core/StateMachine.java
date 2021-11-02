package com.spring2go.common.state.machine.core;

import com.spring2go.common.state.machine.exception.StateMachineException;

import java.util.Set;

/**
 * 状态机
 *
 * @author xiaobin
 */
public interface StateMachine {
    /**
     * Return current FSM state.
     * @return current FSM state
     */
    State getCurrentState();

    /**
     * Return FSM initial state.
     * @return FSM initial state
     */
    State getInitialState();

    /**
     * Return FSM final states.
     * @return FSM final states
     */
    Set<State> getFinalStates();

    /**
     * Return FSM registered states.
     * @return FSM registered states
     */
    Set<State> getStates();

    /**
     * Return FSM registered transitions.
     * @return FSM registered transitions
     */
    Set<Transition> getTransitions();

    /**
     * Return the last triggered event.
     * @return the last triggered event
     */
    Event getLastEvent();

    /**
     * Return the last transition made.
     * @return the last transition made
     */
    Transition getLastTransition();

    /**
     * Fire an event. According to event type, the FSM will make the right transition.
     * @param event to fire
     * @return The next FSM state defined by the transition to make
     * @throws StateMachineException
     */
    State fire(Event event) throws StateMachineException;
}
