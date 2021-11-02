package com.spring2go.common.state.machine.builder;

import com.spring2go.common.state.machine.core.State;
import com.spring2go.common.state.machine.core.StateMachineHandler;
import com.spring2go.common.state.machine.core.Transition;

import java.util.Set;

/**
 * 状态机构建器
 *
 * @author xiaobin
 */
public class StateMachineBuilder {
    private final StateMachineHandler stateMachine;
    private final StateMachineDefinitionValidator stateMachineDefinitionValidator;
    private final TransitionDefinitionValidator transitionDefinitionValidator;

    /**
     * Create a new {@link StateMachineBuilder}.
     *
     * @param states       set of the machine
     * @param initialState of the machine
     */
    public StateMachineBuilder(final Set<State> states, final State initialState) {
        stateMachine = new StateMachineHandler(states, initialState);
        stateMachineDefinitionValidator = new StateMachineDefinitionValidator();
        transitionDefinitionValidator = new TransitionDefinitionValidator();
    }

    /**
     * Register a transition within FSM transitions set.
     * If the transition is not valid, this method may throw an {@link IllegalArgumentException}.
     *
     * @param transition the transition to register
     * @return a configured FSM Builder instance
     */
    public StateMachineBuilder registerTransition(final Transition transition) {
        transitionDefinitionValidator.validateTransitionDefinition(transition, stateMachine);
        stateMachine.registerTransition(transition);
        return this;
    }

    /**
     * Register a set of transitions within FSM transitions set.
     * If a transition is not valid, this method throws an {@link IllegalArgumentException}.
     *
     * @param transitions the transitions set to register
     * @return a configured FSM Builder instance
     */
    public StateMachineBuilder registerTransitions(final Set<Transition> transitions) {
        for (Transition transition : transitions) {
            registerTransition(transition);
        }
        return this;
    }

    /**
     * Register FSM final state which is not mandatory.
     * Once in final state, the FSM will ignore all incoming events.
     *
     * @param finalState the FSM final state
     * @return a configured FSM Builder instance
     */
    public StateMachineBuilder registerFinalState(final State finalState) {
        stateMachine.registerFinalState(finalState);
        return this;
    }

    /**
     * Register FSM final states set.
     * Once in final state, the FSM will ignore all incoming events.
     *
     * @param finalStates the FSM final states to register
     * @return a configured FSM Builder instance
     */
    public StateMachineBuilder registerFinalStates(final Set<State> finalStates) {
        for (State finalState : finalStates) {
            registerFinalState(finalState);
        }
        return this;
    }

    /**
     * Build a FSM instance. This method checks if FSM definition is valid.
     * If FSM state is not valid, this methods throws an {@link IllegalStateException}
     *
     * @return a configured FSM instance
     */
    public StateMachineHandler build() {
        stateMachineDefinitionValidator.validateFiniteStateMachineDefinition(stateMachine);
        return stateMachine;
    }

}
