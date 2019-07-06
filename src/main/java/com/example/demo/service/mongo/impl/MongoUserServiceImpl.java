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
