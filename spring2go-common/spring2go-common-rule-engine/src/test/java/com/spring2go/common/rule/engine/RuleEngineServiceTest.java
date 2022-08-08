package com.spring2go.common.rule.engine;

import com.spring2go.common.rule.engine.config.RuleEngineProperties;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.reader.XmlRuleReader;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//此处classes内的内容是@SpringBootApplication入口
@SpringBootTest(classes = {RuleEngineServiceTest.class})
class RuleEngineServiceTest {

    @Test
    void RunTest() throws RuleEngineException {

        Student student = new Student();
        student.setName("张三");
        student.setAge(18);

        RuleEngineProperties ruleEngineProperties = new RuleEngineProperties();
        XmlRuleReader reader = new XmlRuleReader(ruleEngineProperties);
        RuleEngineService service = new RuleEngineService(reader);
        service.run(student);

        System.out.println(student);

    }

    @Data
    class Student {
        String name;
        Integer age;
        String result;
    }
}