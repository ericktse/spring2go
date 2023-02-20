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

    private static final String BEGIN = "_begin";
    private static final String END = "_end";
    /**
     * 数字类型字段，拼接此后缀 接受多值参数
     */
    private static final String MULTI = "_MultiString";
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
    private static final String LIKE_MYSQL_SPECIAL_STRS = "_,%";
    /**
     * 时间格式化
     */
    private static final ThreadLocal<SimpleDateFormat> LOCAL = new ThreadLocal<SimpleDateFormat>();


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
     * @param queryObj 查询实体
     * @return QueryWrapper实例
     */
    public static <T> QueryWrapper<T> initQueryWrapper(T queryObj, Map<String, String[]> parameterMap) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        initMybatisPlusQueryWrapper(queryWrapper, queryObj, parameterMap);
        return queryWrapper;
    }

    /**
     * 组装Mybatis Plus 查询条件
     * <p>使用此方法 需要有如下几点注意:
     * <br>1.使用QueryWrapper 而非LambdaQueryWrapper;
     * <br>2.实例化QueryWrapper时不可将实体传入参数
     * <br>错误示例:如QueryWrapper<Demo> queryWrapper = new QueryWrapper<Demo>(demo);
     * <br>正确示例:QueryWrapper<Demo> queryWrapper = new QueryWrapper<Demo>();
     * <br>3.也可以不使用这个方法直接调用 {@link #initQueryWrapper}直接获取实例
     */
    private static void initMybatisPlusQueryWrapper(QueryWrapper<?> queryWrapper, Object queryObj, Map<String, String[]> parameterMap) {

        //获取对象属性描述器
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(queryObj);
        //String name, type, column;

        Map<String, String> fieldColumnMap = new HashMap<>(5);
        for (int i = 0; i < propertyDescriptors.length; i++) {

            String name = propertyDescriptors[i].getName();
            String type = propertyDescriptors[i].getPropertyType().toString();
            try {
                if (isUselessField(name) || !PropertyUtils.isReadable(queryObj, name)) {
                    continue;
                }

                Object value = PropertyUtils.getSimpleProperty(queryObj, name);
                String column = getTableFieldName(queryObj.getClass(), name);
                if (column == null) {
                    // column为null只有一种情况 那就是 添加了注解@TableField(exist = false)
                    // 如果字段加注解了@TableField(exist = false),不走DB查询
                    continue;
                }
                fieldColumnMap.put(name, column);
                //区间查询
                doIntervalQuery(queryWrapper, parameterMap, type, name, column);

                //判断单值  参数带不同标识字符串 走不同的查询
                //支持前后带逗号的支持分割后模糊查询(多选字段查询生效) 示例：,1,3,
                if (null != value && value.toString().startsWith(COMMA) && value.toString().endsWith(COMMA)) {
                    String multiLikeVal = value.toString().replace(",,", COMMA);
                    String[] vals = multiLikeVal.substring(1, multiLikeVal.length()).split(COMMA);
                    final String field = ConvertUtils.camelToUnderline(column);
                    if (vals.length > 1) {
                        queryWrapper.and(j -> {
                            log.debug("---查询过滤器，Query规则---field:{}, rule:{}, value:{}", field, "like", vals[0]);
                            j = j.like(field, vals[0]);
                            for (int k = 1; k < vals.length; k++) {
                                j = j.or().like(field, vals[k]);
                                log.debug("---查询过滤器，Query规则 .or()---field:{}, rule:{}, value:{}", field, "like", vals[k]);
                            }
                        });
                    } else {
                        log.debug("---查询过滤器，Query规则---field:{}, rule:{}, value:{}", field, "like", vals[0]);
                        queryWrapper.and(j -> j.like(field, vals[0]));
                    }
                } else {
                    //根据参数值关键字符串判断查询类型
                    QueryRuleEnum rule = convert2Rule(value);
                    value = replaceValue(rule, value);
                    addEasyQuery(queryWrapper, column, rule, value);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
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
                || "sort".equals(name) || "order".equals(name);
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
                    if (!"".equals(column)) {
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
     * 区间查询
     *
     * @param queryWrapper query对象
     * @param parameterMap 参数map
     * @param type         字段类型
     * @param filedName    字段名称
     * @param columnName   列名称
     */
    private static void doIntervalQuery(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap, String type, String filedName, String columnName) throws ParseException {
        // 添加 判断是否有区间查询值
        String endValue = null, beginValue = null;
        if (parameterMap != null && parameterMap.containsKey(filedName + BEGIN)) {
            beginValue = parameterMap.get(filedName + BEGIN)[0].trim();
            addQueryByRule(queryWrapper, columnName, type, beginValue, QueryRuleEnum.GE);

        }
        if (parameterMap != null && parameterMap.containsKey(filedName + END)) {
            endValue = parameterMap.get(filedName + END)[0].trim();
            addQueryByRule(queryWrapper, columnName, type, endValue, QueryRuleEnum.LE);
        }
        //多值查询
        if (parameterMap != null && parameterMap.containsKey(filedName + MULTI)) {
            endValue = parameterMap.get(filedName + MULTI)[0].trim();
            addQueryByRule(queryWrapper, columnName.replace(MULTI, ""), type, endValue, QueryRuleEnum.IN);
        }
    }


    private static void addQueryByRule(QueryWrapper<?> queryWrapper, String name, String type, String value, QueryRuleEnum rule) throws ParseException {
        if (StringUtils.isNotEmpty(value)) {
            // 针对数字类型字段，多值查询
            if (value.contains(COMMA)) {
                Object[] temp = Arrays.stream(value.split(COMMA)).map(v -> {
                    try {
                        return parseByType(v, type, rule);
                    } catch (ParseException e) {
                        log.error("字段类型转换失败", e);
                        return v;
                    }
                }).toArray();
                addEasyQuery(queryWrapper, name, rule, temp);
                return;
            }
            Object temp = parseByType(value, type, rule);
            addEasyQuery(queryWrapper, name, rule, temp);
        }
    }

    /**
     * 根据类型转换给定的值
     *
     * @param value
     * @param type
     * @param rule
     * @return
     * @throws ParseException
     */
    private static Object parseByType(String value, String type, QueryRuleEnum rule) throws ParseException {
        Object temp;
        switch (type) {
            case "class java.lang.Integer":
                temp = Integer.parseInt(value);
                break;
            case "class java.math.BigDecimal":
                temp = new BigDecimal(value);
                break;
            case "class java.lang.Short":
                temp = Short.parseShort(value);
                break;
            case "class java.lang.Long":
                temp = Long.parseLong(value);
                break;
            case "class java.lang.Float":
                temp = Float.parseFloat(value);
                break;
            case "class java.lang.Double":
                temp = Double.parseDouble(value);
                break;
            case "class java.util.Date":
                temp = getDateQueryByRule(value, rule);
                break;
            default:
                temp = value;
                break;
        }
        return temp;
    }

    /**
     * 获取日期类型的值
     *
     * @param value
     * @param rule
     * @return
     * @throws ParseException
     */
    private static Date getDateQueryByRule(String value, QueryRuleEnum rule) throws ParseException {
        Date date = null;
        int length = 10;
        if (value.length() == length) {
            if (rule == QueryRuleEnum.GE) {
                //比较大于
                date = getTime().parse(value + " 00:00:00");
            } else if (rule == QueryRuleEnum.LE) {
                //比较小于
                date = getTime().parse(value + " 23:59:59");
            }
            //TODO 日期类型比较特殊 可能oracle下不一定好使
        }
        if (date == null) {
            date = getTime().parse(value);
        }
        return date;
    }


    private static SimpleDateFormat getTime() {
        SimpleDateFormat time = LOCAL.get();
        if (time == null) {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LOCAL.set(time);
        }
        return time;
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
    private static QueryRuleEnum convert2Rule(Object value) {
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
    private static Object replaceValue(QueryRuleEnum rule, Object value) {
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
            value = specialStrConvert(value.toString());
        } else if (rule == QueryRuleEnum.LEFT_LIKE || rule == QueryRuleEnum.NE) {
            value = val.substring(1);
            //mysql 模糊查询之特殊字符下划线 （_、\）
            value = specialStrConvert(value.toString());
        } else if (rule == QueryRuleEnum.RIGHT_LIKE) {
            value = val.substring(0, val.length() - 1);
            //mysql 模糊查询之特殊字符下划线 （_、\）
            value = specialStrConvert(value.toString());
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
    private static String specialStrConvert(String value) {
        String[] specialStr = LIKE_MYSQL_SPECIAL_STRS.split(",");
        for (String str : specialStr) {
            if (value.indexOf(str) != -1) {
                value = value.replace(str, "\\" + str);
            }
        }
        return value;
    }
}
