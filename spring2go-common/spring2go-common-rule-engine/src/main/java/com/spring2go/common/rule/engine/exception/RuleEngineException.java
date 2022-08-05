package com.spring2go.common.rule.engine.exception;

/**
 * 规则引擎异常
 *
 * @author xiaobin
 */
public class RuleEngineException extends Exception {

    public RuleEngineException() {
        super();
    }

    public RuleEngineException(String message) {
        super(message);
    }

    public RuleEngineException(Throwable cause) {
        super(cause);
    }

    public RuleEngineException(String message,Throwable cause) {
        super(message, cause);
    }


    public RuleEngineException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
