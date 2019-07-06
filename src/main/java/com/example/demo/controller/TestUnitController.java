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