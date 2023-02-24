package com.spring2go.common.mybatis.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring2go.common.core.util.ConvertUtils;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.mybatis.domain.QueryRuleEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mybatis Plus Query Wrapper Utils
 *
 * @author xiaobin
 */
@Slf4j
public class QueryWrapperUtils {

    private static final String BEGIN = "Begin";
    private static final String END = "End";
    private static final String MULTI = "Multi";
    private static final String STAR = "*";
    private static final String COMMA = ",";
    /**
     * 页面带有规则值查询，空格作为分隔符
     */
    private static final String QUERY_SEPARATE_KEYWORD = " ";
    /**
     * 查询 逗号转义符 相当于一个逗号【作废】
     */
    public static final String QUERY_COMMA_ESCAPE = "++";
    private static final String NOT_EQUAL = "!";
    /**
     * 日期格式化yyyy-MM-dd
     */
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * to_date
     */
    private static final String TO_DATE = "to_date";
    /**
     * mysql 模糊查询之特殊字符下划线 （_、\）
     */
    private static final String LIKE_SQL_SPECIAL_STRINGS = "_,%";

    /**
     * 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
     *
     * @param queryObj 查询实体
     * @return QueryWrapper实例
     */
    public static <T> QueryWrapper<T> initQueryWrapper(Object queryObj) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        initMybatisPlusQueryWrapper(queryWrapper, queryObj, null);
        return queryWrapper;
    }

    /**
     * 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
     *
     * @param parameterMap 查询列表
     * @return QueryWrapper实例
     */
    public static <T> QueryWrapper<T> initQueryWrapper(Map<String, String[]> parameterMap) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        initMybatisPlusQueryWrapper(queryWrapper, null, parameterMap);
        return queryWrapper;
    }

    /**
     * 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
     *
     * @param queryObj     查询实体
     * @param parameterMap 查询列表
     * @return QueryWrapper实例
     */
    public static <T> QueryWrapper<T> initQueryWrapper(Object queryObj, Map<String, String[]> parameterMap) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        initMybatisPlusQueryWrapper(queryWrapper, queryObj, parameterMap);
        return queryWrapper;
    }

    /**
     * 组装Mybatis Plus 查询条件
     *
     * @param queryWrapper QueryWrapper实例
     * @param queryObj     查询实体
     * @param parameterMap 查询列表
     */
    private static void initMybatisPlusQueryWrapper(QueryWrapper<?> queryWrapper, Object queryObj, Map<String, String[]> parameterMap) {
        Map<String, Object> fieldColumnMap = new HashMap<>(10);

        if (queryObj != null) {
            //获取对象属性描述器
            PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(queryObj);
            for (int i = 0; i < propertyDescriptors.length; i++) {
                String name = propertyDescriptors[i].getName();
                if (isUselessField(name) || !PropertyUtils.isReadable(queryObj, name)) {
                    continue;
                }

                Object value = null;
                try {
                    value = PropertyUtils.getSimpleProperty(queryObj, name);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                fieldColumnMap.put(name, value);
            }
        }

        if (parameterMap != null) {
            for (Map.Entry<String, String[]> p : parameterMap.entrySet()) {
                String key = p.getKey();
                if (!fieldColumnMap.containsKey(key)) {
                    if (isUselessField(key)) {
                        continue;
                    }
                    String value = p.getValue() == null ? null : p.getValue()[0].trim();
                    fieldColumnMap.put(key, value);
                }
            }
        }

        for (Map.Entry<String, Object> entry : fieldColumnMap.entrySet()) {
            String filedName = entry.getKey();
            Object value = entry.getValue();

            Class<?> clazz = queryObj == null ? queryWrapper.getEntityClass() : queryObj.getClass();
            String columnName = getTableFieldName(clazz, filedName);
            if (columnName == null) {
                // column为null只有一种情况 那就是 添加了注解@TableField(exist = false)
                // 如果字段加注解了@TableField(exist = false),不走DB查询
                continue;
            }

            String actualColumnName = columnName.replaceAll(BEGIN, "")
                    .replaceAll(END, "")
                    .replaceAll(MULTI, "");

            if (isIntervalQuery(columnName, actualColumnName, fieldColumnMap)) {
                //区间查询
                try {
                    addIntervalQuery(queryWrapper, actualColumnName, columnName, value);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //根据参数值关键字符串判断查询类型
                QueryRuleEnum rule = convert2QueryRule(value);
                value = replaceSpecialValue(rule, value);
                addEasyQuery(queryWrapper, columnName, rule, value);
            }
        }
    }

    /**
     * 过滤无用的查询字段
     *
     * @param name
     * @return
     */
    private static boolean isUselessField(String name) {
        return "class".equals(name) || "ids".equals(name)
                || "page".equals(name) || "rows".equals(name)
                || "sort".equals(name) || "order".equals(name)
                || "pageNum".equals(name) || "pageSize".equals(name);
    }

    /**
     * 获取表字段名
     *
     * @param clazz
     * @param name
     * @return
     */
    private static String getTableFieldName(Class<?> clazz, String name) {
        //如果字段加注解了@TableField(exist = false),不走DB查询
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            log.debug("获取 {} 字段数据表字段名异常", name);
        }

        //如果为空，则去父类查找字段
        if (field == null) {
            List<Field> allFields = getClassFields(clazz);
            List<Field> searchFields = allFields.stream().filter(a -> a.getName().equals(name)).collect(Collectors.toList());
            if (searchFields != null && searchFields.size() > 0) {
                field = searchFields.get(0);
            }
        }

        if (field != null) {
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null) {
                if (tableField.exist() == false) {
                    //如果设置了TableField false 这个字段不需要处理
                    return null;
                } else {
                    String column = tableField.value();
                    //如果设置了TableField value 这个字段是实体字段
                    if (!StringUtils.isEmpty(column)) {
                        return column;
                    }
                }
            }
        }
        return name;
    }

    /**
     * 获取class的 包括父类的
     *
     * @param clazz
     * @return
     */
    private static List<Field> getClassFields(Class<?> clazz) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields;
        do {
            fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                list.add(fields[i]);
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && clazz != null);
        return list;
    }

    /**
     * 是否区间查询
     */
    private static boolean isIntervalQuery(String columnName, String actualColumnName, Map<String, Object> fieldColumnMap) {
        if (columnName.endsWith(BEGIN)
                || columnName.endsWith(END)
                || columnName.endsWith(MULTI)) {
            if (fieldColumnMap.containsKey(actualColumnName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 区间查询
     *
     * @param queryWrapper     query对象
     * @param actualColumnName 字段类型
     * @param columnName       列名称
     * @param value            字段值
     */
    private static void addIntervalQuery(QueryWrapper<?> queryWrapper, String actualColumnName, String columnName, Object value) throws ParseException {
        if (columnName.endsWith(BEGIN) && value != null) {
            addEasyQuery(queryWrapper, actualColumnName, QueryRuleEnum.GE, value + " 00:00:00");
        }
        if (columnName.endsWith(END) && value != null) {
            addEasyQuery(queryWrapper, actualColumnName, QueryRuleEnum.LE, value + " 23:59:59");
        }
        if (columnName.endsWith(MULTI) && value != null) {
            addEasyQuery(queryWrapper, actualColumnName, QueryRuleEnum.IN, value);
        }
    }

    /**
     * 根据规则走不同的查询
     *
     * @param queryWrapper QueryWrapper
     * @param name         字段名字
     * @param rule         查询规则
     * @param value        查询条件值
     */
    private static void addEasyQuery(QueryWrapper<?> queryWrapper, String name, QueryRuleEnum rule, Object value) {
        if (value == null || rule == null) {
            return;
        }
        name = ConvertUtils.camelToUnderline(name);
        log.debug("---查询过滤器，Query规则---field:{}, rule:{}, value:{}", name, rule.getValue(), value);
        switch (rule) {
            case GT:
                queryWrapper.gt(name, value);
                break;
            case GE:
                queryWrapper.ge(name, value);
                break;
            case LT:
                queryWrapper.lt(name, value);
                break;
            case LE:
                queryWrapper.le(name, value);
                break;
            case EQ:
            case EQ_WITH_ADD:
                queryWrapper.eq(name, value);
                break;
            case NE:
                queryWrapper.ne(name, value);
                break;
            case IN:
                if (value instanceof String) {
                    queryWrapper.in(name, (Object[]) value.toString().split(COMMA));
                } else if (value instanceof String[]) {
                    queryWrapper.in(name, (Object[]) value);
                } else if (value.getClass().isArray()) {
                    queryWrapper.in(name, (Object[]) value);
                } else {
                    queryWrapper.in(name, value);
                }
                break;
            case LIKE:
                queryWrapper.like(name, value);
                break;
            case LEFT_LIKE:
                queryWrapper.likeLeft(name, value);
                break;
            case RIGHT_LIKE:
                queryWrapper.likeRight(name, value);
                break;
            default:
                log.debug("--查询规则未匹配到---");
                break;
        }
    }

    /**
     * 根据所传的值 转化成对应的比较方式
     * 支持><= like in !
     *
     * @param value
     * @return
     */
    private static QueryRuleEnum convert2QueryRule(Object value) {
        // 避免空数据:查询条件输入空格导致return null后续判断导致抛出null异常
        if (value == null) {
            return QueryRuleEnum.EQ;
        }
        String val = (value + "").toString().trim();
        if (val.length() == 0) {
            return QueryRuleEnum.EQ;
        }
        QueryRuleEnum rule = null;

        // 此处规则，只适用于 le lt ge gt
        // step 1 .>= =<
        int length2 = 2;
        int length3 = 3;
        if (rule == null && val.length() >= length3) {
            if (QUERY_SEPARATE_KEYWORD.equals(val.substring(length2, length3))) {
                rule = QueryRuleEnum.getByValue(val.substring(0, 2));
            }
        }

        // step 2 .> <
        if (rule == null && val.length() >= length2) {
            if (QUERY_SEPARATE_KEYWORD.equals(val.substring(1, length2))) {
                rule = QueryRuleEnum.getByValue(val.substring(0, 1));
            }
        }

        // step 3 like
        // 默认带*就走模糊，但是如果只有一个*，那么走等于查询
        if (rule == null && val.equals(STAR)) {
            rule = QueryRuleEnum.EQ;
        }
        if (rule == null && val.contains(STAR)) {
            if (val.startsWith(STAR) && val.endsWith(STAR)) {
                rule = QueryRuleEnum.LIKE;
            } else if (val.startsWith(STAR)) {
                rule = QueryRuleEnum.LEFT_LIKE;
            } else if (val.endsWith(STAR)) {
                rule = QueryRuleEnum.RIGHT_LIKE;
            }
        }

        // step 4 in
        if (rule == null && val.contains(COMMA)) {
            //TODO in 查询这里应该有个bug  如果一字段本身就是多选 此时用in查询 未必能查询出来
            rule = QueryRuleEnum.IN;
        }

        // step 5 !=
        if (rule == null && val.startsWith(NOT_EQUAL)) {
            rule = QueryRuleEnum.NE;
        }

        // step 6 xx+xx+xx 这种情况适用于如果想要用逗号作精确查询 但是系统默认逗号走in 所以可以用++替换【此逻辑作废】
        if (rule == null && val.indexOf(QUERY_COMMA_ESCAPE) > 0) {
            rule = QueryRuleEnum.EQ_WITH_ADD;
        }

        //特殊处理：Oracle的表达式to_date('xxx','yyyy-MM-dd')含有逗号，会被识别为in查询，转为等于查询
        if (rule == QueryRuleEnum.IN && val.indexOf(YYYY_MM_DD) >= 0 && val.indexOf(TO_DATE) >= 0) {
            rule = QueryRuleEnum.EQ;
        }

        return rule != null ? rule : QueryRuleEnum.EQ;
    }

    /**
     * 替换掉关键字字符
     *
     * @param rule
     * @param value
     * @return
     */
    private static Object replaceSpecialValue(QueryRuleEnum rule, Object value) {
        if (rule == null) {
            return null;
        }
        if (!(value instanceof String)) {
            return value;
        }
        String val = (value + "").toString().trim();
        if (QueryRuleEnum.EQ.getValue().equals(val)) {
            return val;
        }

        if (rule == QueryRuleEnum.LIKE) {
            value = val.substring(1, val.length() - 1);
            //mysql 模糊查询之特殊字符下划线 （_、\）
            value = convertSpecialSqlString(value.toString());
        } else if (rule == QueryRuleEnum.LEFT_LIKE || rule == QueryRuleEnum.NE) {
            value = val.substring(1);
            //mysql 模糊查询之特殊字符下划线 （_、\）
            value = convertSpecialSqlString(value.toString());
        } else if (rule == QueryRuleEnum.RIGHT_LIKE) {
            value = val.substring(0, val.length() - 1);
            //mysql 模糊查询之特殊字符下划线 （_、\）
            value = convertSpecialSqlString(value.toString());
        } else if (rule == QueryRuleEnum.IN) {
            value = val.split(",");
        } else if (rule == QueryRuleEnum.EQ_WITH_ADD) {
            value = val.replaceAll("\\+\\+", COMMA);
        } else {
            if (val.startsWith(rule.getValue())) {
                //TODO 此处逻辑应该注释掉-> 如果查询内容中带有查询匹配规则符号，就会被截取的（比如：>=您好）
                value = val.replaceFirst(rule.getValue(), "");
            } else if (val.startsWith(rule.getCondition() + QUERY_SEPARATE_KEYWORD)) {
                value = val.replaceFirst(rule.getCondition() + QUERY_SEPARATE_KEYWORD, "").trim();
            }
        }
        return value;
    }

    /**
     * mysql 模糊查询之特殊字符下划线 （_、\）
     *
     * @param value:
     * @Return: java.lang.String
     */
    private static String convertSpecialSqlString(String value) {
        String[] specialStr = LIKE_SQL_SPECIAL_STRINGS.split(",");
        for (String str : specialStr) {
            if (value.indexOf(str) != -1) {
                value = value.replace(str, "\\" + str);
            }
        }
        return value;
    }
}
