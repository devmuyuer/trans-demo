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