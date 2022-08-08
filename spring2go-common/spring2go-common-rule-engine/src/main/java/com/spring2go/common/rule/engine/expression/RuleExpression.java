package com.spring2go.common.rule.engine.expression;

import com.spring2go.common.rule.engine.entity.*;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.executor.ComplexRuleExecutor;
import com.spring2go.common.rule.engine.util.RuleCacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则表达式解析器
 *
 * @author xiaobin
 */
public class RuleExpression extends AbstractExpression {

    /**
     * 解析
     *
     * @param express
     * @return
     */
    @Override
    public ExpressionUnit compile(String express) {
        List<OperationUnit> stack = formatExpress(express);
        expressionUnit = breakExpress(stack, null);
        return expressionUnit;
    }

    @Override
    public Object execute(Object fact) throws RuleEngineException {
        Boolean result = checkExpress(expressionUnit, fact);
        return result;
    }

    public void executeSub(ExpressionUnit unit, Object fact, List<RuleResult> results) throws RuleEngineException {
        if (null == unit) {
            unit = expressionUnit;
        }
        //如果是规则变量，则执行规则计算
        if (OperationType.VARIABLE.equals(unit.getType())) {
            if (unit.getValue()) {
                Rule rule = RuleCacheUtils.getRule(unit.getOperator());
                if (null != rule) {
                    ComplexRuleExecutor ruleExecutor = new ComplexRuleExecutor();
                    RuleResult ret = ruleExecutor.execute(rule, fact);
                    if (null != ret) {
                        results.add(ret);
                    }
                }
            }
        } else {
            executeSub(unit.getLeft(), fact, results);
            executeSub(unit.getRight(), fact, results);
        }
    }

    private boolean checkExpress(ExpressionUnit unit, Object fact) throws RuleEngineException {
        if (unit.getType() == OperationType.NOT) {
            unit.setValue(!checkExpress(unit.getRight(), fact));
        } else if (unit.getType() == OperationType.AND) {
            boolean temp = checkExpress(unit.getLeft(), fact);
            if (!temp) {
                unit.setValue(false);
            } else {
                unit.setValue(temp && checkExpress(unit.getRight(), fact));
            }
        } else if (unit.getType() == OperationType.OR) {
            boolean temp = checkExpress(unit.getLeft(), fact);
            if (temp) {
                unit.setValue(true);
            } else {
                unit.setValue(temp || checkExpress(unit.getRight(), fact));
            }
        } else if (unit.getType() == OperationType.VARIABLE) {
            //如果是规则变量，则执行规则计算
            Rule rule = RuleCacheUtils.getRule(unit.getOperator());
            if (null != rule) {
                ComplexRuleExecutor ruleExecutor = new ComplexRuleExecutor();
                unit.setValue(ruleExecutor.check(rule, fact));
            }
        }
        return unit.getValue();
    }

    /**
     * 解析出各个独立的单元。
     *
     * @param express
     * @return
     */
    private List<OperationUnit> formatExpress(String express) {
        List<OperationUnit> theStack = new ArrayList<OperationUnit>();
        StringBuffer element = new StringBuffer();
        int level = 0;
        OperationUnit unit = null;

        for (int i = 0; i < express.length(); i++) {
            char alphabet = express.charAt(i);
            switch (alphabet) {
                case '(':
                    if (element.length() > 0) {
                        unit = new OperationUnit(element.toString(), OperationType.VARIABLE, level);
                        theStack.add(unit);
                    }
                    level++;            // ( 本身也属于下个level.
                    unit = new OperationUnit(String.valueOf(alphabet), OperationType.LEFT_BRACKET, level);
                    theStack.add(unit);
                    element = new StringBuffer();
                    break;
                case ')':
                    if (element.length() > 0) {
                        unit = new OperationUnit(element.toString(), OperationType.VARIABLE, level);
                        theStack.add(unit);
                    }
                    unit = new OperationUnit(String.valueOf(alphabet), OperationType.RIGHT_BRACKET, level);
                    theStack.add(unit);
                    level--;                    // ) 本身也属于上一个level.
                    element = new StringBuffer();
                    break;
                case '!':
                    if (element.length() > 0) {
                        unit = new OperationUnit(element.toString(), OperationType.VARIABLE, level);
                        theStack.add(unit);
                    }
                    unit = new OperationUnit(String.valueOf(alphabet), OperationType.NOT, level);
                    theStack.add(unit);
                    element = new StringBuffer();
                    break;
                case '&':
                    //如果是2个||或者是2个&&，那么就是逻辑运算符
                    if (express.length() >= i + 1 && express.charAt(i + 1) == alphabet) {
                        if (element.length() > 0) {
                            unit = new OperationUnit(element.toString(), OperationType.VARIABLE, level);
                            theStack.add(unit);
                        }
                        unit = new OperationUnit(String.valueOf(alphabet) + String.valueOf(alphabet), OperationType.AND, level);
                        theStack.add(unit);
                        i++;
                        element = new StringBuffer();
                    } else {
                        element.append(alphabet);
                    }
                    break;
                case '|':
                    //如果是2个||或者是2个&&，那么就是逻辑运算符
                    if (express.length() >= i + 1 && express.charAt(i + 1) == alphabet) {
                        if (element.length() > 0) {
                            unit = new OperationUnit(element.toString(), OperationType.VARIABLE, level);
                            theStack.add(unit);
                        }
                        unit = new OperationUnit(String.valueOf(alphabet) + String.valueOf(alphabet), OperationType.OR, level);
                        theStack.add(unit);
                        i++;
                        element = new StringBuffer();
                    } else {
                        element.append(alphabet);
                    }
                    break;
                case ' ':         //去除空格。
                case '\n':        //去除换行。
                case '\r':        //去除回车。
                    break;
                default:
                    element.append(alphabet);
                    break;
            }
        }

        //扫尾的字符串也要添加进去。
        if (element.length() > 0) {
            String end = element.toString();
            if (")".equals(end)) {
                //level 必须是1
                unit = new OperationUnit(end, OperationType.RIGHT_BRACKET, 1);
                theStack.add(unit);

            } else if ("!".equals(end) || "(".equals(end) || "|".equals(end) || "&".equals(end)) {
                System.err.println("结尾的字符不能是关键字。");
            } else {
                //变量
                unit = new OperationUnit(end, OperationType.VARIABLE, 0);
                theStack.add(unit);
            }
        }

        return theStack;
    }

    /**
     * 去除前后端无用的括号。
     *
     * @param list
     * @param minLevel -- 如果是-1，那么需要重新累计level值。
     * @return
     */
    private List<OperationUnit> trimExpress(List<OperationUnit> list, int minLevel) {

        //无括号表达式。
        if (list.size() <= 1) {
            return list;
        }

        //开头不是左括号
        if (list.get(0).getType() != OperationType.LEFT_BRACKET) {
            return list;
        }

        //开头是左括号，但是结尾不是右括号。
        if (list.get(0).getType() == OperationType.LEFT_BRACKET && list.get(list.size() - 1).getType() != OperationType.RIGHT_BRACKET) {
            return list;
        }

        //最少括号数目是0，就是中间有非括号的情况 () + ()。
        if (minLevel == 0) {
            return list;
        }

        //未知的最小括号数目，重新取得。
        if (minLevel < 0) {
            int tempLevel = Integer.MAX_VALUE;
            for (OperationUnit unit : list) {
                if (tempLevel > unit.getLevel()) {
                    tempLevel = unit.getLevel();
                }
            }

            minLevel = tempLevel;
        }

        //最少括号数目是0，就是中间有非括号的情况 () + ()。
        if (minLevel <= 0) {
            return list;
        }

        //依次拷贝到去除多余括号的数组中去。
        List<OperationUnit> newList = new ArrayList<OperationUnit>();
        for (int i = 0; i < list.size(); i++) {
            OperationUnit unit = list.get(i);
            if (i < minLevel) {
                //非标准括号，可能是表达式不合格。
                if (unit.getType() != OperationType.LEFT_BRACKET) {
                    System.err.println("括号个数不匹配。");
                }
                ;
            } else if (i >= list.size() - minLevel) {
                if (unit.getType() != OperationType.RIGHT_BRACKET) {
                    System.err.println("括号个数不匹配。");
                }
            } else {
                unit.setLevel(unit.getLevel() - minLevel);
                newList.add(unit);
            }
        }

        return newList;
    }

    /**
     * 分解表达式，将复杂的表达式分解成基础单元（比如：括号，简单的表达式等。）
     *
     * @param list
     * @param current
     * @return
     * @throws RuleEngineException
     */
    private ExpressionUnit breakExpress(List<OperationUnit> list, ExpressionUnit current) {

        list = trimExpress(list, -1);

        ExpressionUnit root = current;
        if (root == null) {
            root = new ExpressionUnit();
        }

        int firstOr = -1, firstAnd = -1, firstNot = -1;
        for (int i = 0; i < list.size(); i++) {
            OperationUnit unit = list.get(i);
            //取得括号外的内容。括号中的内容不作为划分的信息。
            if (unit.getLevel() == 0) {
                //双目运算符
                if (unit.getType() == OperationType.OR && firstOr == -1) {
                    firstOr = i;
                    break;
                } else if (unit.getType() == OperationType.AND && firstAnd == -1) {
                    firstAnd = i;
                } else if (unit.getType() == OperationType.NOT && firstNot == -1) {
                    firstNot = i;
                }
            }
        }

        if (firstOr > 0) {
            root.setOperator(list.get(firstOr).getElement());
            //root.setName("OR");
            root.setType(OperationType.OR);
            root.setLeftSubList(this.trimExpress(list.subList(0, firstOr), -1));
            root.setLeft(breakExpress(root.getLeftSubList(), root.getLeft()));
            root.setRightSubList(this.trimExpress(list.subList(firstOr + 1, list.size()), -1));
            root.setRight(breakExpress(root.getRightSubList(), root.getRight()));

        } else if (firstAnd > 0) {
            root.setOperator(list.get(firstAnd).getElement());
            //root.setName("AND");
            root.setType(OperationType.AND);
            root.setLeftSubList(this.trimExpress(list.subList(0, firstAnd), -1));
            root.setLeft(breakExpress(root.getLeftSubList(), root.getLeft()));
            root.setRightSubList(this.trimExpress(list.subList(firstAnd + 1, list.size()), -1));
            root.setRight(breakExpress(root.getRightSubList(), root.getRight()));

        } else if (firstNot >= 0) {
            root.setOperator(list.get(firstNot).getElement());
            //root.setName("NOT");
            root.setType(OperationType.NOT);
            root.setLeftSubList(null);
            root.setRightSubList(this.trimExpress(list.subList(firstNot + 1, list.size()), -1));
            root.setRight(breakExpress(root.getRightSubList(), root.getRight()));
        } else if (current == null && list.size() > 0) {
            //不带运算符的情况。
            if (list.get(0).getType() == OperationType.VARIABLE) {
                root.setOperator(list.get(0).getElement());
                //root.setName("VARIABLE");
                root.setType(OperationType.VARIABLE);
            }
        }

        return root;
    }

}
