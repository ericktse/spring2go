package com.spring2go.common.state.machine.core;

import com.spring2go.common.state.machine.exception.StateMachineException;

import java.util.HashSet;
import java.util.Set;

/**
 * 默认状态机处理器
 *
 * @author xiaobin
 */
public class StateMachineHandler implements StateMachine {
    private State currentState;
    private final State initialState;
    private final Set<State> finalStates;
    private final Set<State> states;
    private final Set<Transition> transitions;
    private Event lastEvent;
    private Transition lastTransition;

    public StateMachineHandler(final Set<State> states, final State initialState) {
        this.states = states;
        this.initialState = initialState;
        currentState = initialState;
        transitions = new HashSet<>();
        finalStates = new HashSet<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public final synchronized State fire(final Event event) throws StateMachineException {

        if (!finalStates.isEmpty() && finalStates.contains(currentState)) {
            return currentState;
        }

        if (event == null) {
            return currentState;
        }

        for (Transition transition : transitions) {
            if (
                    currentState.equals(transition.getSourceState()) && //fsm is in the right state as expected by transition definition
                            transition.getEventType().equals(event.getClass()) && //fired event type is as expected by transition definition
                            states.contains(transition.getTargetState()) //target state is defined
            ) {
                try {
                    //perform action, if any
                    if (transition.getEventHandler() != null) {
                        transition.getEventHandler().handle(event);
                    }
                    //transit to target state
                    currentState = transition.getTargetState();

                    //save last triggered event and transition
                    lastEvent = event;
                    lastTransition = transition;

                    break;
                } catch (Exception e) {
                    throw new StateMachineException(transition, event, e);
                }
            }
        }
        return currentState;
    }

    public void registerTransition(final Transition transition) {
        transitions.add(transition);
    }

    public void registerFinalState(final State finalState) {
        finalStates.add(finalState);
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    @Override
    public State getInitialState() {
        return initialState;
    }

    @Override
    public Set<State> getFinalStates() {
        return finalStates;
    }

    @Override
    public Set<State> getStates() {
        return states;
    }

    @Override
    public Set<Transition> getTransitions() {
        return transitions;
    }

    @Override
    public Event getLastEvent() {
        return lastEvent;
    }

    @Override
    public Transition getLastTransition() {
        return lastTransition;
    }
}
