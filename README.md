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
     └── spring2go-job -- 分布式定时任务管理台 [5003]
```