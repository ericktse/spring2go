
## 配置实例
```xml
<scenes id="subscribe" name="认购" model="com.spring2go.subscribe.SubscribeModel">
    <state id="new" name="新增" >
        <condition>
            //满足新增条件，如数据库不存在数据或者通过表达式判断状态为新增
            <class>com.spring2go.subscribe.NewCondition</class>
            <expression>state=='new'</expression>
        </condition>
        <event>
            <class>com.spring2go.subscribe.NewEvent</class>
        </event>
    </state>
    <state id="change" name="换房" >
        <condition>
            <class>com.spring2go.subscribe.ChangeCondition</class>
        </condition>
        <event>
            <class>com.spring2go.subscribe.ChangeEvent</class>
        </event>
    </state>
    <state id="close" name="关闭" >
        <condition>
            <class>com.spring2go.subscribe.CloseCondition</class>
        </condition>
        <event>
            <class>com.spring2go.subscribe.CloseEvent</class>
        </event>
    </state>
</scenes>
```