package com.spring2go.common.state.engine.core;

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


}
