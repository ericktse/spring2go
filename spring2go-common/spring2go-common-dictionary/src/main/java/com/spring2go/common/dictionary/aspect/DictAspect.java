package com.spring2go.common.dictionary.aspect;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.constant.SecurityConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.core.annotation.Dict;
import com.spring2go.common.redis.util.RedisUtils;
import com.spring2go.system.feign.service.RemoteDictService;
import com.spring2go.system.vo.DictModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 字典AOP
 *
 * @author xiaobin
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
public class DictAspect {

    private final RedisUtils redisUtils;
    private final RemoteDictService remoteDictService;

    private final static long EXPIRE_TIME = 300;
    private final static String DICT_CACHE_KEY = "sys:cache:dict:%s:%s";
    private final static String DICT_TABLE_CACHE_KEY = "sys:cache:dict:table:%s:%s:%s:%s";


    @Pointcut("execution(public * com.spring2go..*.*Controller.*(..))")
    public void doPointcut() {
    }

    @Around("doPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        //获取执行结果
        Object result = pjp.proceed();

        long start = System.currentTimeMillis();
        result = this.parseDictText(result);
        long end = System.currentTimeMillis();
        log.debug("注入字典到JSON数据,耗时" + (end - start) + "ms");
        return result;
    }

    /**
     * 本方法针对返回对象为R的数据进行动态字典注入
     * 字典注入实现 @IPage的分页列表 @xx
     * 通过对实体类添加注解@dict 来标识需要的字典内容,字典分为单字典code即可 ，table字典 code table text配合使用
     * 示例:
     * SysUser  字段为sex 添加了注解@Dict(code = "sex") 会在字典服务立马查出来对应的text 然后在请求list的时候将这个字典text，已字段名称加_dictText形式返回到前端
     * 例如输入当前返回值的就会多出一个sex_dictText字段
     * {
     * sex:1,
     * sex_dictText:"男"
     * }
     * 前端直接取值sex_dictText在table里面无需再进行前端的字典转换了
     * 目前vue是这么进行字典渲染到table上的多了就很麻烦了 这个直接在服务端渲染完成前端可以直接用
     *
     * @param result
     */
    private Object parseDictText(Object result) {
        if (result instanceof R) {
            if (((R) result).getData() instanceof IPage) {
                List<Object> records = ((IPage) ((R) result).getData()).getRecords();

                Boolean hasDict = checkHasDict(records);
                if (!hasDict) {
                    return result;
                }

                //遍历所有数据，解析字典注解字段
                List<JSONObject> jsonRecords = new ArrayList<>();
                for (Object record : records) {

                    String jsonString = JSON.toJSONString(record);
                    JSONObject jsonObject = JSONObject.parseObject(jsonString);
                    // 遍历所有字段
                    for (Field field : getAllFields(record)) {
                        //筛选出加了 Dict 注解的字段列表
                        if (field.getAnnotation(Dict.class) != null) {
                            field.setAccessible(true);
                            String name = field.getName();
                            String value = jsonObject.getString(name);
                            if (StringUtils.isEmpty(value)) {
                                continue;
                            }

                            Dict dict = field.getAnnotation(Dict.class);
                            String code = dict.code();
                            String table = dict.table();
                            String text = dict.text();

                            //translate
                            String dictName = field.getName() + CommonConstants.DICT_TEXT_SUFFIX;
                            Object dictValue = translateDict(code, table, text, value);

                            jsonObject.put(dictName, dictValue);
                        }
                    }
                    jsonRecords.add(jsonObject);
                }
                ((IPage) ((R) result).getData()).setRecords(jsonRecords);
            }
        }
        return result;
    }

    /**
     * 获取类的所有属性，包括父类
     *
     * @param object
     * @return
     */
    private Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 检测返回结果集中是否包含Dict注解
     *
     * @param records
     * @return
     */
    private Boolean checkHasDict(List<Object> records) {
        if (null != records && records.size() > 0) {
            for (Field field : getAllFields(records.get(0))) {
                if (null != field.getAnnotation(Dict.class)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 翻译字典,执行一次后缓存，
     * 1.  所有的普通数据字典的所有数据只执行一次SQL
     * 2.  表字典相同的所有数据只执行一次SQL
     *
     * @author xiaobin
     */
    private Object translateDict(String code, String table, String text, String value) {

        String dictResult = null;
        if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(table) && StringUtils.isNotEmpty(text)) {
            //table模式
            String keyString = String.format(DICT_TABLE_CACHE_KEY, table, code, text, value);
            //如果缓存有，则从缓存取
            dictResult = redisUtils.getCacheObject(keyString);
            if (null == dictResult) {
                //value 传null，把table所有字典值一次查询出来
                R<List<DictModel>> result = remoteDictService.queryDictFromTable(table, code, text, null, SecurityConstants.FROM_IN);
                if (result.getCode() == CommonConstants.SUCCESS) {
                    List<DictModel> models = result.getData();
                    for (DictModel model : models) {
                        if (model.getCode() == code && model.getValue() == value) {
                            dictResult = model.getText();
                        }
                        String key = String.format(DICT_TABLE_CACHE_KEY, table, code, text, model.getValue());
                        redisUtils.setCacheObject(key, model.getText(), EXPIRE_TIME, TimeUnit.SECONDS);
                    }
                }
            }
        } else if (StringUtils.isNotEmpty(code)) {
            //字典模式
            String keyString = String.format(DICT_CACHE_KEY, code, value);
            //如果缓存有，则从缓存取
            dictResult = redisUtils.getCacheObject(keyString);
            if (null == dictResult) {
                R<List<DictModel>> result = remoteDictService.queryDict(code, SecurityConstants.FROM_IN);
                if (result.getCode() == CommonConstants.SUCCESS) {
                    List<DictModel> models = result.getData();
                    for (DictModel model : models) {
                        if (model.getCode() == code && model.getValue() == value) {
                            dictResult = model.getText();
                        }
                        String key = String.format(DICT_CACHE_KEY, code, model.getValue());
                        redisUtils.setCacheObject(key, model.getText(), EXPIRE_TIME, TimeUnit.SECONDS);
                    }
                }
            }
        }
        return dictResult;
    }
}
