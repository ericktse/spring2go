package com.spring2go.common.state.machine.core;

import lombok.Data;
import lombok.ToString;

/**
 * @author xiaobin
 * @description 状态
 * @date 2021/11/1
 */
@Data
@ToString
public class State {
    private final String name;

    /**
     * Create a new {@link State}.
     *
     * @param name of the state
     */
    public State(final String name) {
        this.name = name;
    }

    /**
     * Get state name.
     * @return state name
     */
    public String getName() {
        return name;
    }

    /*
     * States have unique name within a Easy States FSM instance
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        State state = (State) o;

        return name.equals(state.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
