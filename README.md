## 核心依赖

| 依赖                   | 版本          |
| ---------------------- | ------------- |
| Java            | 1.8 |
| Spring Boot            | 2.4.4 |
| Spring Cloud           | 2020.0.2    |
| Spring Cloud Alibaba   | 2.2.1.RELEASE |

## 模块说明
```lua
spring2go
├── spring2go-ui  --前端UI
├── spring2go-auth -- 授权服务提供[3000]
└── spring2go-common -- 系统公共模块
     ├── spring2go-common-core -- 公共工具类核心包
     ├── spring2go-common-datasource -- 动态数据源包
     ├── spring2go-common-job -- xxl-job 封装
     ├── spring2go-common-log -- 日志服务
     ├── spring2go-common-mybatis -- mybatis 扩展封装
     ├── spring2go-common-security -- 安全工具类
     ├── spring2go-common-swagger -- 接口文档
     ├── spring2go-common-feign -- feign 扩展封装
     └── spring2go-common-test -- oauth2.0 单元测试扩展封装
├── spring2go-register -- Nacos Server[8848]
├── spring2go-gateway -- Spring Cloud Gateway网关[9999]
└── spring2go-modules -- 业务模块
     └── spring2go-upms-api -- 通用用户权限管理系统公共api模块
     └── spring2go-upms-biz -- 通用用户权限管理系统业务处理模块 [4000]
└── spring2go-visual
     └── spring2go-monitor -- 服务监控 [5001]
     ├── spring2go-codegen -- 图形化代码生成 [5002]
     └── spring2go-xxl-job -- 分布式定时任务管理台 [5003]
```

## 微服务技术栈
SpringCloud Alibaba 技术栈

1. 服务注册发现 Nacos √
2. 统一配置中心 Nacos √
3. 路由网关 Gateway √
4. 分布式 http feign √
5. 熔断降级、限流 Sentinel √
6. 分布式任务 xxl-job √
7. Redisson分布式锁 √
8. 服务监控 SpringBootAdmin √
9. 消息中间件 RabbitMQ √
10. 消息总线 √
11. 分布式事务 Seata 
12. 支持 docker-compose、jenkins √
13. 支持 k8s
14. 分库分表 shardingsphere 
15. 分布式文件 Minio、阿里OSS √
16. 统一权限控制 JWT + Shiro √
17. CAS 单点登录
18. 链路跟踪 Skywarking 
19. 分布式日志 elk + kafaka


## 待办事项
- spring2go-ui
- spring2go-nacos
- spring2go-sentinel
- spring2go-common-seata
- spring2go-common-shardingsphere
- spring2go-common-rocketmq
- spring2go-modules
  - spring2go-demo-api
  - spring2go-demo-biz