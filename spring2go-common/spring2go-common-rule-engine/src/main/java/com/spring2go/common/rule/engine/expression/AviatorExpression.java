package com.spring2go.common.rule.engine.expression;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.spring2go.common.rule.engine.entity.ExpressionUnit;
import com.spring2go.common.rule.engine.exception.RuleEngineException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * Aviator表达式解释器
 *
 * @author xiaobin
 */
public class AviatorExpression extends AbstractExpression {

    @Override
    public ExpressionUnit compile(String express) {
        Expression expression = AviatorEvaluator.compile(express);
        expressionUnit = new ExpressionUnit();
        expressionUnit.setCompiledExpression(expression);
        return expressionUnit;
    }

    @Override
    public Object execute(Object fact) throws RuleEngineException {
        Expression expression = (Expression) expressionUnit.getCompiledExpression();
        Object obj = expression.execute(objectToMap(fact));
        return obj;
    }

    /**
     * 对象转map
     *
     * @param obj
     * @return
     */
    private Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Field[] allFields = obj.getClass().getDeclaredFields();
        for (Field field : allFields) {
            // Skip this if you intend to access to public fields only
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
