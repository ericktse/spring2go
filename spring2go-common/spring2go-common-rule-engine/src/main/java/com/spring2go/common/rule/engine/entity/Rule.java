package com.spring2go.common.rule.engine.entity;

import com.spring2go.common.core.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * 规则实例
 *
 * @author xiaobin
 */
@Data
@Slf4j
public class Rule {

    private String id;
    private String description;
    private String whenSql;
    private String whenSqlParamValue;
    private String whenSqlParamType;
    private String whenSqlCompareValue;
    private String whenSqlCompareOperate;
    private String whenClass;
    private String whenExpression;
    private String whenRuleExpression;

    private String parent;
    private int priority = 0;
    private boolean loop = false;
    private boolean enabled = true;

    private String thenSql;
    private String thenSqlParamValue;
    private String thenSqlParamType;
    private String thenClass;
    private String thenExpressionValue;
    private String thenExpressionParam;


    public Boolean set(String name, Object value) {
        Class<?> clazz = this.getClass();
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            if (field.getType() == int.class || field.getType() == Integer.class) {
                field.set(this, Integer.parseInt(value.toString()));
            } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                field.set(this, Boolean.parseBoolean(value.toString()));
            } else if (field.getType() == double.class || field.getType() == Double.class) {
                field.set(this, Double.parseDouble(value.toString()));
            } else {
                field.set(this, value);
            }
            return true;
        } catch (Exception e) {
            log.error("rule init field error: " + name, e);
        }
        return false;
    }

    public Boolean isSubRule() {
        if (StringUtils.isEmpty(parent)) {
            return false;
        }
        return true;
    }
}
