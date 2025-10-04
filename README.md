# mvn-test 项目

## 项目简介

mvn-test 是一个基于 Spring MVC + JPA/Hibernate + Apache Shiro 的企业级权限管理系统。该项目实现了完整的 RBAC(基于角色的访问控制)权限管理模型,包含用户管理、角色管理、权限管理和菜单管理等核心功能模块。

## 技术栈

### 核心框架
- **Spring Framework 3.2.0**: IoC 容器和 MVC 框架
- **Spring Data JPA 1.4.2**: 数据访问层框架
- **Hibernate 4.1.3**: ORM 持久化框架
- **Apache Shiro 1.2.0**: 安全框架,提供认证和授权功能

### 数据库
- **MySQL 5.x**: 关系型数据库
- **Commons DBCP 1.4**: 数据库连接池

### 缓存
- **Ehcache 2.5.2**: 本地缓存
- **Spymemcached 2.8.1**: Memcached 客户端,用于分布式缓存

### 视图层
- **JSP/JSTL 1.2**: 视图模板
- **Sitemesh 2.4.2**: 页面装饰框架
- **Jackson 1.9.6**: JSON 处理

### 工具库
- **Apache Commons Lang3 3.1**: 通用工具类
- **Joda-Time 2.1**: 日期时间处理
- **SLF4J + Log4j**: 日志框架

### 构建工具
- **Maven**: 项目构建和依赖管理
- **Jetty Maven Plugin**: 开发调试服务器
- **JRebel**: 热部署工具

## 项目结构

```
mvn-test/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/rootls/base/
│   │   │       ├── bean/              # Bean 配置类
│   │   │       │   ├── Constants.java
│   │   │       │   ├── DataTable.java
│   │   │       │   └── SaltAwareJdbcRealm.java
│   │   │       ├── cache/             # 缓存相关
│   │   │       │   ├── CacheManager.java
│   │   │       │   ├── CachedAspect.java
│   │   │       │   ├── NeedCached.java
│   │   │       │   ├── ServiceCache.java
│   │   │       │   ├── MemcachedUtils.java
│   │   │       │   └── SpyMemcached*.java
│   │   │       ├── model/             # 实体类
│   │   │       │   ├── IdEntity.java
│   │   │       │   ├── User.java
│   │   │       │   ├── Role.java
│   │   │       │   ├── Permission.java
│   │   │       │   └── Menu.java
│   │   │       ├── repository/        # 数据访问层
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── RoleRepository.java
│   │   │       │   ├── PermissionRepository.java
│   │   │       │   ├── MenuRepository.java
│   │   │       │   └── custom/        # 自定义仓储接口和实现
│   │   │       ├── service/           # 业务逻辑层
│   │   │       │   ├── UserService.java
│   │   │       │   ├── RoleService.java
│   │   │       │   ├── PermissionService.java
│   │   │       │   ├── MenuService.java
│   │   │       │   └── impl/
│   │   │       ├── util/              # 工具类
│   │   │       │   ├── CookieUtils.java
│   │   │       │   ├── MD5Utils.java
│   │   │       │   ├── SpringUtils.java
│   │   │       │   ├── GenericsUtils.java
│   │   │       │   ├── DynamicSpecifications.java
│   │   │       │   └── UrlBuilder.java
│   │   │       └── view/              # 视图层
│   │   │           ├── controller/    # 控制器
│   │   │           │   ├── LoginContrlloer.java
│   │   │           │   ├── BaseController.java
│   │   │           │   └── manage/
│   │   │           │       ├── UserController.java
│   │   │           │       ├── RoleController.java
│   │   │           │       ├── PermissionsController.java
│   │   │           │       └── MenuController.java
│   │   │           ├── command/       # 表单命令对象
│   │   │           ├── groups/        # 验证分组
│   │   │           ├── inteceptor/    # 拦截器
│   │   │           └── tag/           # 自定义标签
│   │   ├── resources/
│   │   │   ├── applicationContext.xml
│   │   │   ├── spring/
│   │   │   │   ├── applicationContext-cache.xml
│   │   │   │   ├── applicationContext-dataSource.xml
│   │   │   │   ├── applicationContext-jpa.xml
│   │   │   │   └── applicationContext-shiro.xml
│   │   │   ├── ehcache.xml
│   │   │   ├── log4j.properties
│   │   │   └── memcached.properties
│   │   ├── sql/
│   │   │   └── data.sql              # 数据库初始化脚本
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           ├── views/            # JSP 视图文件
│   │           │   ├── login.jsp
│   │           │   ├── home.jsp
│   │           │   └── manage/
│   │           ├── layout/           # 页面布局
│   │           ├── web.xml
│   │           └── decorators.xml
│   └── test/                         # 测试代码
└── pom.xml                           # Maven 配置文件
```

## 主要功能

### 1. 用户管理
- 用户的增删改查
- 用户密码加密存储(SHA-256)
- 为用户分配角色
- 用户信息维护

### 2. 角色管理
- 角色的创建、编辑、删除
- 为角色分配权限
- 角色描述和创建时间管理
- 用户与角色的多对多关联

### 3. 权限管理
- 细粒度权限控制
- 支持权限分组
- 权限与角色的关联管理
- 基于 Shiro 的权限验证

### 4. 菜单管理
- 树形菜单结构
- 支持父子菜单关系
- 菜单排序功能
- 菜单URL配置

### 5. 安全认证
- 基于 Apache Shiro 的安全框架
- 用户登录认证
- 记住我功能
- 会话管理
- 未授权页面处理

### 6. 缓存机制
- 支持 Ehcache 本地缓存
- 支持 Memcached 分布式缓存
- AOP 切面实现缓存注解
- 自定义缓存管理器

## 数据模型

### 核心实体关系
```
User (用户)
  ├── 多对多 -> Role (角色)

Role (角色)
  ├── 多对多 -> User (用户)
  └── 多对多 -> Permission (权限)

Permission (权限)
  ├── 多对多 -> Role (角色)
  └── 自关联 -> Permission (权限组)

Menu (菜单)
  └── 自关联 -> Menu (父菜单)
```

## 使用方法

### 环境要求
- JDK 1.6 或更高版本
- Maven 3.x
- MySQL 5.x
- Tomcat 6.x 或 Jetty 8.x

### 数据库配置

1. 创建数据库并导入初始数据:
```bash
mysql -u root -p < src/main/sql/data.sql
```

2. 修改数据库连接配置:
编辑 `src/main/filter/mavenFilters.properties` 文件,配置数据库连接信息。

### 编译打包

```bash
# 编译项目
mvn clean compile

# 打包为 WAR 文件
mvn clean package
```

### 运行项目

#### 使用 Jetty 运行(开发环境)
```bash
mvn jetty:run
```
访问地址: http://localhost:9091/mvn-test

#### 部署到 Tomcat
```bash
# 部署到远程 Tomcat
mvn cargo:deploy

# 或手动部署
# 将 target/mvn-test.war 复制到 Tomcat 的 webapps 目录
```

### 初始用户

系统预置了以下测试用户:

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | admin | 管理员 | 拥有所有权限 |
| user1 | user1 | 普通用户 | 部分权限 |
| luowei | clong | 普通用户 | 部分权限 |
| user2 | (见数据库) | 顾客 | 只读权限 |

## 依赖说明

### Spring 相关依赖
- **spring-context**: Spring 核心容器,提供 IoC 功能
- **spring-orm**: Spring ORM 支持,整合 Hibernate
- **spring-webmvc**: Spring MVC 框架
- **spring-data-jpa**: 简化 JPA 数据访问层开发

### 持久化相关
- **hibernate-core**: Hibernate 核心库
- **hibernate-entitymanager**: JPA 实现
- **hibernate-validator**: JSR-303 数据验证
- **hibernate-ehcache**: Hibernate 二级缓存支持

### 安全框架
- **shiro-core**: Shiro 核心库
- **shiro-web**: Shiro Web 支持
- **shiro-spring**: Shiro 与 Spring 集成
- **shiro-ehcache**: Shiro 缓存支持

### 其他关键依赖
- **aspectjweaver**: AOP 切面支持
- **jackson-mapper-asl**: JSON 序列化/反序列化
- **mysql-connector-java**: MySQL JDBC 驱动
- **slf4j-api + slf4j-log4j12**: 日志框架

## 配置文件说明

### Spring 配置
- `applicationContext.xml`: 主配置文件
- `applicationContext-dataSource.xml`: 数据源配置
- `applicationContext-jpa.xml`: JPA/Hibernate 配置
- `applicationContext-shiro.xml`: Shiro 安全配置
- `applicationContext-cache.xml`: 缓存配置

### 其他配置
- `web.xml`: Web 应用配置
- `log4j.properties`: 日志配置
- `ehcache.xml`: Ehcache 缓存配置
- `memcached.properties`: Memcached 缓存配置
- `dataSource.properties`: 数据源属性配置

## 开发环境配置

### Maven Profiles
项目支持多环境配置:

- **dev**: 开发环境
- **test**: 测试环境

使用方式:
```bash
# 使用开发环境配置构建
mvn clean package -Pdev

# 使用测试环境配置构建
mvn clean package -Ptest
```

### JRebel 热部署
项目集成了 JRebel 插件,支持热部署:
```bash
mvn jrebel:generate
```

## 技术特点

1. **分层架构**: 严格的分层设计(Controller -> Service -> Repository)
2. **依赖注入**: 使用 Spring IoC 实现松耦合
3. **ORM 映射**: 基于 JPA/Hibernate 的对象关系映射
4. **安全控制**: 基于 Apache Shiro 的细粒度权限控制
5. **缓存支持**: 多级缓存策略,提升系统性能
6. **声明式事务**: Spring 事务管理
7. **AOP 编程**: 面向切面的缓存和日志处理
8. **Repository 模式**: Spring Data JPA 简化数据访问
9. **密码加密**: 使用 SHA-256 + Salt 加密用户密码
10. **页面装饰**: 使用 Sitemesh 统一页面布局

## 注意事项

1. 本项目使用较老版本的框架(Spring 3.x, Hibernate 4.x),主要用于学习和参考
2. 生产环境建议升级到更新的框架版本
3. 数据库密码等敏感配置应通过环境变量或配置中心管理
4. 建议根据实际需求调整 JPA 二级缓存配置
5. Memcached 配置需要单独部署 Memcached 服务

## 作者

luowei (luowei@rootls.com)

## 许可证

本项目仅供学习和参考使用。
