package com.spring2go.common.rule.engine.expression;

/**
 * 操作类型
 *
 * @author xiaobin
 */
public final class OperationType {
    public static final String VARIABLE ="VARIABLE";// 变量
    public static final String AND="&&";// 且
    public static final String OR="||";// 或
    public static final String NOT="!"; // 非
    public static final String LEFT_BRACKET="="; // 左括号
    public static final String RIGHT_BRACKET=")"; // 右括号
    public static final String EQUAL="=="; // 等于
    public static final String GREATER=">"; // 大于
    public static final String LESS="<"; // 小于
    public static final String NOT_EQUAL="!="; // 不等于
    public static final String GREATER_EQUAL=">="; // 大于等于
    public static final String LESS_EQUAL="<="; // 小于等于
    public static final String CONTAIN="contain"; // 包含
    public static final String NOT_CONTAIN="!contain"; // 不包含
    public static final String MEMBER="member"; // 被包含
    public static final String NOT_MEMBER="!member"; // 不被包含
    public static final String MATCH="match"; // 满足正则表达式
    public static final String NOT_MATCH="!match"; // 不满足正则表达式
}
