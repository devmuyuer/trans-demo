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
