package com.example.demo.service.primary.impl;

import com.example.demo.common.R;
import com.example.demo.common.RUtil;
import com.example.demo.common.SystemException;
import com.example.demo.entity.primary.PrimaryUser;
import com.example.demo.repository.primary.PrimaryUserRepository;
import com.example.demo.service.primary.PrimaryUserService;
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
public class PrimaryUserServiceImpl implements PrimaryUserService {

    @Autowired
    PrimaryUserRepository primaryUserRepository;


    /**
     * 新增
     * @param primaryUser
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public R save(PrimaryUser primaryUser) {
        PrimaryUser userSave = primaryUserRepository.save(primaryUser);
        log.info("用户信息保存：userSave {}", userSave);
        return RUtil.success("");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public R bathSave(String unitId, Boolean rollBack) {
        for (int i = 0; i <= 10; i++) {

            //注释这段则可以正常添加数据，测试回滚则throw异常信息
            if (unitId.equals("003") && rollBack) {
                throw new SystemException("测试回滚故意抛出的异常");
            }

            PrimaryUser user = new PrimaryUser();
            user.setUserId(unitId + "U0" + i);
            user.setUserName("用户" + i);
            user.setUnitId(unitId);
            save(user);
        }
        return RUtil.success("");
    }
}
