package com.example.demo.service.slave.impl;

import com.example.demo.common.R;
import com.example.demo.common.RUtil;
import com.example.demo.common.SystemException;
import com.example.demo.entity.mongo.MongoUser;
import com.example.demo.entity.slave.SlaveUser;
import com.example.demo.repository.mongo.MongoUserRepository;
import com.example.demo.repository.slave.SlaveUserRepository;
import com.example.demo.service.slave.SlaveUserService;
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
public class SlaveUserServiceImpl implements SlaveUserService {

    @Autowired
    SlaveUserRepository slaveUserRepository;


    /**
     * 新增
     * @param user
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public R save(SlaveUser user) {
        SlaveUser userSave = slaveUserRepository.save(user);
        log.info("用户信息保存：userSave {}", userSave);
        return RUtil.success("");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public R bathSave(String unitId, Boolean rollBack) {
        for (int i = 0; i < 10; i++) {

            //注释这段则可以正常添加数据，测试回滚则throw异常信息
            if (unitId.equals("003") && rollBack) {
                throw new SystemException("测试回滚故意抛出的异常");
            }

            SlaveUser user = new SlaveUser();
            user.setUserId(unitId + "U0" + i);
            user.setUserName("用户" + i);
            user.setUnitId(unitId);
            save(user);
        }
        return RUtil.success("");
    }
}
