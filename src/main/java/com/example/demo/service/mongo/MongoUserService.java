package com.example.demo.service.mongo;

import com.example.demo.entity.mongo.MongoUser;
import com.example.demo.common.R;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface MongoUserService {


    /**
     * 保存数据
     * @param user
     * @return
     */
    R save(MongoUser user);

    /**
     * 批量保存
     * @param unitId
     * @param rollBack
     * @return
     */
    R bathSave(String unitId, Boolean rollBack);
}
