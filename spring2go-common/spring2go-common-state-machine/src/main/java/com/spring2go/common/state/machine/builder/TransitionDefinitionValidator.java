package com.spring2go.common.state.machine.builder;

import com.spring2go.common.state.machine.core.State;
import com.spring2go.common.state.machine.core.StateMachine;
import com.spring2go.common.state.machine.core.Transition;

/**
 * 转换定义校验器
 *
 * @author xiaobin
 */
public class TransitionDefinitionValidator {
    /**
     * Validate transition definition :
     * <ul>
     *     <li>Source and target states must be declared in FSM states set.</li>
     *     <li>Source and target states must be defined (not null)</li>
     *     <li>Event Type must be defined (not null)</li>
     *     <li>Event Handler is not mandatory</li>
     * </ul>
     *
     * @param transition the transition to validate
     */
    void validateTransitionDefinition(final Transition transition, StateMachine finiteStateMachine) {

        String transitionName = transition.getName();
        State sourceState = transition.getSourceState();
        State targetState = transition.getTargetState();

        if (sourceState == null) {
            throw new IllegalArgumentException("No source state is defined for transition '" + transitionName + "'");
        }
        if (targetState == null) {
            throw new IllegalArgumentException("No target state is defined for transition '" + transitionName + "'");
        }
        if (transition.getEventType() == null) {
            throw new IllegalArgumentException("No event type is defined for transition '" + transitionName + "'");
        }
        if (!finiteStateMachine.getStates().contains(sourceState)) {
            throw new IllegalArgumentException("Source state '" + sourceState.getName() + "' is not registered in FSM states for transition '" + transitionName + "'");
        }
        if (!finiteStateMachine.getStates().contains(targetState)) {
            throw new IllegalArgumentException("target state '" + targetState.getName() + "' is not registered in FSM states for transition '" + transitionName + "'");
        }
    }
}
