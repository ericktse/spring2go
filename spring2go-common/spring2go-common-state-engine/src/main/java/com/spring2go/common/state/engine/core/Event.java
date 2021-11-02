package com.spring2go.common.state.engine.core;

import com.spring2go.common.state.engine.context.ScenesModel;

/**
 * 事件
 *
 * @author xiaobin
 */
public interface Event<M extends ScenesModel> {

    String getName();

    void handle(M model);
}
