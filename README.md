# Mongo Transaction
#### 项目介绍

- springboot 2.1.3

- MongoDB 4.0.3

- 本项目主要为了测试MongoDB事务,由于正式项目还用了其它数据源，所以加入了 Oracle, MySQL的事务，包括多数据源的配置和使用


#### 使用说明
1. 导入MongoDB的依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```
2. 配置MongoDB的连接

```
spring:
  # mongodb 连接
  data:
    mongodb:
      uri: mongodb://192.168.0.68:27017,192.168.0.69:27017,192.168.0.70:27017/glcloud?replicaSet=rs0
      database: glcloud
```
3. 编写entity类

当id设置为 **ObjectId** 类型和添加 **@Id** 注解时时，MongoDB数据库会自动生成主键，我们在保存对象时就不用设置id的值

MongoUnit
```java
/**
 * 用户
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
@Data
@Document(collection = "test_unit")
public class MongoUnit {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Id
    private ObjectId id;
    /**
     * unitId
     */
    private String unitId;

    /**
     * unitName
     */
    private String unitName;

}
```
MongoUser
```java
package com.example.demo.entity.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 用户
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
@Data
@Document(collection = "test_user")
public class MongoUser {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Id
    private ObjectId id;
    /**
     * userId
     */
    private String userId;

    /**
     * userName
     */
    private String userName;

    /**
     * unitId 关联testUser
     */
    private String unitId;
}

```

4. 编写dao层的方法

只需继承MongoRepository即可。

```java
package com.example.demo.repository.mongo;

import com.example.demo.entity.mongo.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface MongoUserRepository extends MongoRepository<MongoUser, String> {


}
```
```java
package com.example.demo.repository.mongo;

import com.example.demo.entity.mongo.MongoUnit;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface MongoUnitRepository extends MongoRepository<MongoUnit, String> {

}

```
5. Service层
```java
package com.example.demo.service.mongo.impl;

import com.example.demo.common.SystemException;
import com.example.demo.entity.mongo.MongoUser;
import com.example.demo.repository.mongo.MongoUserRepository;
import com.example.demo.service.mongo.MongoUserService;
import com.example.demo.common.R;
import com.example.demo.common.RUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
@Service
@Slf4j
public class MongoUserServiceImpl implements MongoUserService {

    @Autowired
    MongoUserRepository mongoUserRepository;


    /**
     * 新增
     * @param mongoUser
     * @return
     */
    @Override
    public R save(MongoUser mongoUser) {
        MongoUser mongoUserSave = mongoUserRepository.save(mongoUser);
        log.info("用户信息保存：testUserSave = "+ mongoUserSave);
        return RUtil.success("");
    }

    @Override
    @Transactional(value = "MONGO_TRANSACTION_MANAGER", propagation = Propagation.REQUIRED)
    public R bathSave(String unitId, Boolean rollBack) {
        for (int i = 0; i <= 10; i++) {

            //注释这段则可以正常添加数据，测试回滚则throw异常信息
            if (unitId.equals("003") && rollBack) {
                throw new SystemException("测试回滚故意抛出的异常");
            }

            MongoUser user = new MongoUser();
            user.setUserId(unitId + "U0" + i);
            user.setUserName("用户" + i);
            user.setUnitId(unitId);
            save(user);
        }
        return RUtil.success("");
    }
}

```
```java
package com.example.demo.service.mongo.impl;

import com.example.demo.enums.REnum;
import com.example.demo.common.SystemException;
import com.example.demo.entity.mongo.MongoUnit;
import com.example.demo.repository.mongo.MongoUnitRepository;
import com.example.demo.service.mongo.MongoUnitService;
import com.example.demo.service.mongo.MongoUserService;
import com.example.demo.common.R;
import com.example.demo.common.RUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
@Service
@Slf4j
public class MongoUnitServiceImpl implements MongoUnitService {

    @Autowired
    MongoUnitRepository mongoUnitRepository;
    @Autowired
    MongoUserService mongoUserService;


    /**
     * 新增
     *
     * @param unit
     * @return
     */
    @Override
    public R save(MongoUnit unit) {
        MongoUnit mongoUnitSave = mongoUnitRepository.save(unit);
        log.info("单位信息保存：testUnitSave = " + mongoUnitSave);
        return RUtil.success("");
    }

    @Override
    @Transactional(value = "MONGO_TRANSACTION_MANAGER")
    public R bathSave(Boolean rollBack) {
        try {
            for (int i = 0; i < 4; i++) {

                MongoUnit unit = new MongoUnit();
                unit.setUnitId("00" + i);
                unit.setUnitName("单位" + i);
                mongoUserService.bathSave(unit.getUnitId(),rollBack);

                save(unit);
            }
            return RUtil.success("");
        } catch (SystemException e) {
            log.error("保存数据失败：msg: {}", e.getMessage());
            throw new SystemException(REnum.ERROR.getCode(), "保存数据失败 Error:" + e.getMessage());
        }
    }
}

```

6. Controller
```java
package com.example.demo.controller;

import com.example.demo.enums.DbTypeEnum;
import com.example.demo.service.mongo.MongoUserService;
import com.example.demo.common.R;
import com.example.demo.service.primary.PrimaryUserService;
import com.example.demo.service.slave.SlaveUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author muyuer 182443947@qq.com
 * @date 2019-02-25 10:59
 */
@RestController
@Slf4j
@RequestMapping(path="test/user")
public class TestUserController {

    @Autowired
    MongoUserService mongoUserService;
    @Autowired
    PrimaryUserService primaryUserService;
    @Autowired
    SlaveUserService slaveUserService;

    /**
     * 新增
     * @param dbType
     * @param unitId
     * @param rollBack
     * @return
     */
    @PostMapping("/bathSave/{dbType}/{unitId}/{rollBack}")
    public R bathSave(@PathVariable DbTypeEnum dbType, @PathVariable String unitId, @PathVariable Boolean rollBack){
        switch (dbType) {
            case MONGO:
                return mongoUserService.bathSave(unitId, rollBack);
            case PRIMARY:
                return primaryUserService.bathSave(unitId, rollBack);
            default:
                return slaveUserService.bathSave(unitId, rollBack);
        }
    }
}
```
```java
package com.example.demo.controller;

import com.example.demo.enums.DbTypeEnum;
import com.example.demo.service.mongo.MongoUnitService;
import com.example.demo.common.R;
import com.example.demo.service.primary.PrimaryUnitService;
import com.example.demo.service.slave.SlaveUnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author muyuer 182443947@qq.com
 * @date 2019-02-25 10:59
 */
@RestController
@Slf4j
@RequestMapping(path="test/unit")
public class TestUnitController {

    @Autowired
    MongoUnitService mongoUnitService;
    @Autowired
    PrimaryUnitService primaryUnitService;
    @Autowired
    SlaveUnitService slaveUnitService;

    /**
     * 新增
     * @param dbType 数据库
     * @param rollBack 是否回滚
     * @return
     */
    @PostMapping("/bathSave/{dbType}/{rollBack}")
    public R bathSave(@PathVariable DbTypeEnum dbType, @PathVariable Boolean rollBack) {
        switch (dbType) {
            case MONGO:
                return mongoUnitService.bathSave(rollBack);
            case PRIMARY:
                return primaryUnitService.bathSave(rollBack);
            default:
                return slaveUnitService.bathSave(rollBack);
        }
    }
}
```
## 测试
PostMan post 地址 

MONGO库 不回滚 [http://localhost:8077/test/unit/bathSave/MONGO/0](http://localhost:8077/test/unit/bathSave/MONGO/0)

MONGO库 回滚   [http://localhost:8077/test/unit/bathSave/MONGO/1](http://localhost:8077/test/unit/bathSave/MONGO/1)

Oracle库 不回滚 [http://localhost:8077/test/unit/bathSave/PRIMARY/0](http://localhost:8077/test/unit/bathSave/PRIMARY/0)

Oracle库 回滚   [http://localhost:8077/test/unit/bathSave/PRIMARY/1](http://localhost:8077/test/unit/bathSave/PRIMARY/1)

MySQL库 不回滚 [http://localhost:8077/test/unit/bathSave/SLAVE/0](http://localhost:8077/test/unit/bathSave/SLAVE/0)

MySQL库 回滚   [http://localhost:8077/test/unit/bathSave/SLAVE/1](http://localhost:8077/test/unit/bathSave/SLAVE/1)

### 在实际应用中爬过的坑
- 1.MongoDB的版本必须是4.0
- 2.MongoDB事务功能必须是在多副本集的情况下才能使用，否则报错"Sessions are not supported by the MongoDB cluster to which this client is connected"，4.2版本会支持分片事务。
- 3.事务控制只能用在已存在的集合中，也就是集合需要手工添加不会由jpa创建会报错"Cannot create namespace glcloud.test_user in multi-document transaction."
- 4.多数据源时需要指定事务 @Transactional(value = "transactionManager") 如果只有1个数据源不需要指定value
- 5.事务注解到类上时，该类的所有 public 方法将都具有该类型的事务属性，但一般都是注解到方法上便于实现更精确的事务控制
- 6.事务传递性，事务子方法上不必添加事务注解，如果子方法也提供api调用可用注解propagation = Propagation.REQUIRED也就是继承调用它的事务，如果没有事务则新起一个事务
- 7.启动类上的@EnableTransactionManagement注解，并不是像网上所说必需添加的注解，因为spring boot 默认开始了这个注解的。
- 8.有人说：注解必须是@Transactional(rollbackFor = { Exception.class }) 测试并不需要rollbackFor = { Exception.class },因为本例中自定义异常类继承自RuntimeException spring boot事物默认在遇到RuntimeException不论rollbackFor的异常是啥，都会进行事务的回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
 
  具体rollbackFor用法可参考:
  
  [Spring中的@Transactional(rollbackFor = Exception.class)属性详解](https://www.cnblogs.com/clwydjgs/p/9317849.html)
  
  [一次Spring Transactional嵌套事务使用不同的rollbackFor的分析](https://www.cnblogs.com/null-qige/p/9243720.html)

### 参考文档
* [Spring Data MongoDB Transactions](https://www.baeldung.com/spring-data-mongodb-transactions)
* [MongoDB事务](https://blog.51cto.com/l0vesql/2134631)

