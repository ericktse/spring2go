package com.spring2go.common.state.engine.core;

import com.spring2go.common.state.engine.context.ScenesModel;

/**
 * 条件
 *
 * @author xiaobin
 */
public interface Condition<M extends ScenesModel> {

    String getName();

    String getType();

    Boolean check(M model);
}
