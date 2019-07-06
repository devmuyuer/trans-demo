package com.example.demo.service.slave;

import com.example.demo.common.R;
import com.example.demo.entity.slave.SlaveUnit;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface SlaveUnitService {


    /**
     * 保存数据
     * @param unit
     * @return
     */
    R save(SlaveUnit unit);
    /**
     * 批量保存
     * @param rollBack
     * @return
     */
    R bathSave(Boolean rollBack);
}
