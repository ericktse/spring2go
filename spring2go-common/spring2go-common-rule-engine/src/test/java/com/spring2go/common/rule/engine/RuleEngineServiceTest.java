package com.spring2go.common.rule.engine;

import com.spring2go.common.rule.engine.config.RuleEngineProperties;
import com.spring2go.common.rule.engine.entity.Student;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.reader.XmlRuleReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


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
        Assert.notNull(service, "Engine Service不为空");
    }

    @Test
    void RunClassTest() throws RuleEngineException {

        Student student = new Student();
        student.setName("张三");
        student.setAge(18);

        RuleEngineProperties ruleEngineProperties = new RuleEngineProperties();
        XmlRuleReader reader = new XmlRuleReader(ruleEngineProperties);
        RuleEngineService service = new RuleEngineService(reader);
        service.run(student, "demo_class");
        System.out.println(student);
        Assert.isTrue(student.getName().equals("李四"), "名字变更为李四");
    }

    @Test
    void RunExpressionTest() throws RuleEngineException {

        Student student = new Student();
        student.setName("张三");
        student.setAge(18);

        RuleEngineProperties ruleEngineProperties = new RuleEngineProperties();
        XmlRuleReader reader = new XmlRuleReader(ruleEngineProperties);
        RuleEngineService service = new RuleEngineService(reader);
        service.run(student, "demo_expression");
        System.out.println(student);
        Assert.isTrue(student.getAge().equals(19), "年龄+1");

    }

    @Test
    void RunRuleExpressionTest() throws RuleEngineException {

        Student student = new Student();
        student.setName("张三");
        student.setAge(18);

        RuleEngineProperties ruleEngineProperties = new RuleEngineProperties();
        XmlRuleReader reader = new XmlRuleReader(ruleEngineProperties);
        RuleEngineService service = new RuleEngineService(reader);
        service.run(student, "demo_rule_expression");
        System.out.println(student);
        Assert.isTrue(student.getAge().equals(8), "年龄改为8");

    }

    @Test
    void RunSqlTest() throws RuleEngineException {

        Student student = new Student();
        student.setName("张三");
        student.setAge(18);

        RuleEngineProperties ruleEngineProperties = new RuleEngineProperties();
        XmlRuleReader reader = new XmlRuleReader(ruleEngineProperties);
        RuleEngineService service = new RuleEngineService(reader);
        service.run(student, "demo_sql");
        System.out.println(student);
        Assert.notNull(student, "学生实体不为空");

    }
}