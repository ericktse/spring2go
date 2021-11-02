package com.spring2go.common.state.machine.builder;

import com.spring2go.common.state.machine.core.State;
import com.spring2go.common.state.machine.core.StateMachine;

import java.util.Set;

/**
 * TODO
 *
 * @author xiaobin
 */
public class StateMachineDefinitionValidator {
    /**
     * Deterministic FSM validation : for each state, exactly one outgoing transition for an event type must be defined.
     */
    public void validateFiniteStateMachineDefinition(StateMachine finiteStateMachine) {

        Set<State> states = finiteStateMachine.getStates();

        //check if initial state belongs to FSM declared states.
        State initialState = finiteStateMachine.getInitialState();
        if (!states.contains(initialState)) {
            throw new IllegalStateException("Initial state '" + initialState.getName() + "' must belong to FSM states: " +
                    dumpStates(states));
        }

        //check if registered final states belong to FSM declared states.
        for (State finalState : finiteStateMachine.getFinalStates()) {
            if (!states.contains(finalState)) {
                throw new IllegalStateException("Final state '" + finalState.getName() + "' must belong to FSM states: " +
                        dumpStates(states));
            }
        }
    }

    public String dumpStates(final Set<State> states) {
        StringBuilder result = new StringBuilder();
        for (State state : states) {
            result.append(state.getName()).append(";");
        }
        return result.toString();
    }

}
