package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.core.util.SpringContextHolder;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.entity.RuleResultStatus;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.expression.OperationType;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * SQL规则执行器
 *
 * @author xiaobin
 */
@Slf4j
public class SqlRuleExecutor extends AbstractRuleExecutor {
    @Override
    public Boolean check(Rule rule, Object object) throws RuleEngineException {

        String sqlStr = rule.getWhenSql();

        if (StringUtils.isEmpty(sqlStr)) {
            throw new RuleEngineException("This function must be called when the sql statement is not empty.");
        }

        // add tailed space.
        if (sqlStr.endsWith("?")) {
            sqlStr = sqlStr + " ";
        }

        String paramName = rule.getWhenSqlParamValue();
        String paramType = rule.getWhenSqlParamType();
        List<Object> paramList = null;
        if (!StringUtils.isEmpty(paramName)) {

            String[] params = paramName.split(",");
            String[] sqlSection = sqlStr.split("\\?");

            if (sqlSection.length != params.length + 1) {
                throw new RuleEngineException(String.format("the parameters count must be equal to the count of the question mark.rule:{}", rule.getId()));
            }

            if (StringUtils.isEmpty(paramType)) {
                throw new RuleEngineException(String.format("the cannot be empty if param is assigned.rule:{}", rule.getId()));
            }

            String[] paramTypes = paramType.split(",");
            if (params.length != paramTypes.length) {
                throw new RuleEngineException(String.format("the parameters count must be equal to the count of the question mark.rule:{}", rule.getId()));
            }

            paramList = new ArrayList<Object>();
            for (int i = 0; i < paramTypes.length; i++) {
                String type = paramTypes[i];
                paramList.add(getParamValue(params[i], type));
            }
        }

        Map<String, Object> resultSet = executeSQL(sqlStr, paramList);
        Boolean bRet = compare(resultSet, rule);

        return bRet;
    }

    @Override
    public RuleResult execute(Rule rule, Object object) throws RuleEngineException {

        String sqlStr = rule.getThenSql();

        if (StringUtils.isEmpty(sqlStr)) {
            throw new RuleEngineException("This function must be called when the sql statement is not empty.");
        }

        // add tailed space.
        if (sqlStr.endsWith("?")) {
            sqlStr = sqlStr + " ";
        }

        String paramName = rule.getThenSqlParamValue();
        String paramType = rule.getThenSqlParamType();
        List<Object> paramList = null;
        if (!StringUtils.isEmpty(paramName)) {

            String[] params = paramName.split(",");
            String[] sqlSection = sqlStr.split("\\?");

            if (sqlSection.length != params.length + 1) {
                throw new RuleEngineException(String.format("the parameters count must be equal to the count of the question mark.rule:{}", rule.getId()));
            }

            if (StringUtils.isEmpty(paramType)) {
                throw new RuleEngineException(String.format("the cannot be empty if param is assigned.rule:{}", rule.getId()));
            }

            String[] paramTypes = paramType.split(",");
            if (params.length != paramTypes.length) {
                throw new RuleEngineException(String.format("the parameters count must be equal to the count of the question mark.rule:{}", rule.getId()));
            }

            paramList = new ArrayList<Object>();
            for (int i = 0; i < paramTypes.length; i++) {
                String type = paramTypes[i];
                paramList.add(getParamValue(params[i], type));
            }
        }

        Map<String, Object> resultSet = executeSQL(sqlStr, paramList);
        RuleResult result = new RuleResult(rule);
        result.setStatus(RuleResultStatus.PASSED);

        return result;
    }

    /**
     * 根据Param的类型和名称，把Object对象转换成实际的值。
     *
     * @param param
     * @param type
     * @return
     */
    private Object getParamValue(String param, String type) {

        Object value = "";
        switch (type.toLowerCase()) {

            case "java.lang.integer":
            case "integer":
                value = Integer.parseInt(param);
                break;
            case "java.lang.long":
            case "long":
                value = Long.parseLong(param);
                break;
            case "java.lang.boolean":
            case "boolean":
                value = Boolean.parseBoolean(param);
                break;
            case "java.lang.byte":
            case "byte":
                value = Byte.parseByte(param);
                break;
            case "java.lang.double":
            case "double":
                value = Double.parseDouble(param);
                break;
            case "java.lang.float":
            case "float":
                value = Float.parseFloat(param);
                break;
            case "java.lang.date":
            case "date":
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                value = sdf.format(param);
                break;
            case "java.math.bigdecimal":
            case "bigdecimal":
                value = new BigDecimal(param);
                break;
            case "java.lang.character":
            case "char":
            case "character":
                value = Character.valueOf(param.charAt(0));
                break;
            case "object":
            default:
                value = param;
                break;
        }

        return value;
    }

    private Map<String, Object> executeSQL(String sqlStr, List<Object> paramList) throws RuleEngineException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {

            Connection conn = getConnection();
            stmt = conn.prepareStatement(sqlStr);

            if (null != paramList && !paramList.isEmpty()) {
                for (int i = 0; i < paramList.size(); i++) {

                    Object param = paramList.get(i);
                    //从1开始计数。
                    if (param instanceof String) {
                        stmt.setString(i + 1, param.toString());
                    } else if (param instanceof Integer) {
                        stmt.setInt(i + 1, ((Integer) param).intValue());
                    } else if (param instanceof Long) {
                        stmt.setLong(i + 1, ((Long) param).longValue());
                        // java.util.Date类型对应于timestamp结构了类型。
                    } else if (param instanceof Date) {
                        stmt.setTimestamp(i + 1, new java.sql.Timestamp(((Date) param).getTime()));
                    } else if (param instanceof java.sql.Date) {
                        stmt.setDate(i + 1, (java.sql.Date) param);
                    } else if (param instanceof java.sql.Time) {
                        stmt.setTime(i + 1, (java.sql.Time) param);
                    } else if (param instanceof java.sql.Timestamp) {
                        stmt.setTimestamp(i + 1, (java.sql.Timestamp) param);
                    } else if (param instanceof Double) {
                        stmt.setDouble(i + 1, ((Double) param).doubleValue());
                    } else if (param instanceof BigDecimal) {
                        stmt.setBigDecimal(i + 1, (BigDecimal) param);
                    } else if (param instanceof Boolean) {
                        stmt.setBoolean(i + 1, ((Boolean) param).booleanValue());
                    } else if (param instanceof Byte) {
                        stmt.setByte(i + 1, ((Byte) param).byteValue());
                    } else {
                        stmt.setObject(i + 1, param);
                    }
                }
            }

            res = stmt.executeQuery();
            ResultSetMetaData columnInfo = res.getMetaData();
            int columnCount = columnInfo.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String colName = columnInfo.getColumnName(i);
                retMap.put(colName, res.getObject(i));
            }

            res.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuleEngineException(e);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuleEngineException(e);
        }

        return retMap;
    }

    private Connection getConnection() throws RuleEngineException {
        DataSource ds = SpringContextHolder.getBean("dataSource");

        try {
            return ds.getConnection();
        } catch (SQLException e) {
            log.error("get sql connection error.", e);
            throw new RuleEngineException(e);
        }
    }

    private Boolean compare(Map<String, Object> sqlResult, Rule rule) throws RuleEngineException {

        //获取sql查询结果
        String sqlValue = null;
        if (sqlResult.size() >= 1) {
            // read the first data.
            String firstKey = sqlResult.keySet().stream().findFirst().get();
            if (null == sqlResult.get(firstKey)) {
                sqlValue = null;
            } else if (sqlResult.get(firstKey) instanceof String) {
                sqlValue = (String) sqlResult.get(firstKey);
            } else {
                sqlValue = sqlResult.get(firstKey).toString();
            }
        } else {
            throw new RuleEngineException("sql result is empty.");
        }

        String cValue = rule.getWhenSqlCompareValue();
        String cOperate = rule.getWhenSqlCompareOperate();
        if (null == cValue || null == cOperate) {
            throw new RuleEngineException("null pointer error of subject or baseline or comparison code.");
        }

        boolean cRet = false;
        switch (cOperate) {
            case OperationType.EQUAL:
                try {
                    BigDecimal source = new BigDecimal(sqlValue);
                    BigDecimal object = new BigDecimal(cValue);
                    cRet = source.compareTo(object) == 0;
                } catch (Exception e) {
                    cRet = sqlValue.equals(cValue);
                }
                break;
            case OperationType.GREATER:
                try {
                    BigDecimal source = new BigDecimal(sqlValue);
                    BigDecimal object = new BigDecimal(cValue);
                    cRet = source.compareTo(object) > 0;
                } catch (Exception e) {
                    cRet = sqlValue.compareTo(cValue) > 0;
                }
                break;
            case OperationType.LESS:
                try {
                    BigDecimal source = new BigDecimal(sqlValue);
                    BigDecimal object = new BigDecimal(cValue);
                    cRet = source.compareTo(object) < 0;
                } catch (Exception e1) {
                    cRet = sqlValue.compareTo(cValue) < 0;
                }
                break;
            case OperationType.NOT_EQUAL:
                try {
                    BigDecimal source = new BigDecimal(sqlValue);
                    BigDecimal object = new BigDecimal(cValue);
                    cRet = source.compareTo(object) != 0;
                } catch (Exception e) {
                    cRet = !sqlValue.equals(cValue);
                }
                break;
            case OperationType.GREATER_EQUAL:
                try {
                    BigDecimal source = new BigDecimal(sqlValue);
                    BigDecimal object = new BigDecimal(cValue);
                    cRet = source.compareTo(object) >= 0;
                } catch (Exception e) {
                    cRet = sqlValue.compareTo(cValue) >= 0;
                }
                break;
            case OperationType.LESS_EQUAL:
                try {
                    BigDecimal source = new BigDecimal(sqlValue);
                    BigDecimal object = new BigDecimal(cValue);
                    cRet = source.compareTo(object) <= 0;
                } catch (Exception e) {
                    cRet = sqlValue.compareTo(cValue) <= 0;
                }
                break;
            case OperationType.CONTAIN:
                cRet = sqlValue.contains(cValue);
                break;
            case OperationType.NOT_CONTAIN:
                cRet = !sqlValue.contains(cValue);
                break;
            case OperationType.MEMBER:
                cRet = cValue.contains(sqlValue);
                break;
            case OperationType.NOT_MEMBER:
                cRet = !cValue.contains(sqlValue);
                break;
            case OperationType.MATCH:
                cRet = sqlValue.matches(cValue);
                break;
            case OperationType.NOT_MATCH:
                cRet = !sqlValue.matches(cValue);
                break;
            default:
                break;
        }

        return cRet;
    }
}
