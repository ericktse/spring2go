package com.spring2go.common.openai.exception;

/**
 * OpenAI自定义异常
 *
 * @author xiaobin
 */
public class OpenAiException extends RuntimeException {
    /**
     * Constructs a new ChatException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public OpenAiException(String message) {
        super(message);
    }
}
