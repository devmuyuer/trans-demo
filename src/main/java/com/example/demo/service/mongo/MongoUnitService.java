package com.example.demo.service.mongo;

import com.example.demo.entity.mongo.MongoUnit;
import com.example.demo.common.R;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface MongoUnitService {


    /**
     * 保存数据
     * @param unit
     * @return
     */
    R save(MongoUnit unit);
    /**
     * 批量保存
     * @param rollBack
     * @return
     */
    R bathSave(Boolean rollBack);
}
