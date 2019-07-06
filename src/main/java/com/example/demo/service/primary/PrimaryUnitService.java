package com.example.demo.service.primary;

import com.example.demo.common.R;
import com.example.demo.entity.primary.PrimaryUnit;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface PrimaryUnitService {


    /**
     * 保存数据
     * @param unit
     * @return
     */
    R save(PrimaryUnit unit);
    /**
     * 批量保存
     * @param rollBack
     * @return
     */
    R bathSave(Boolean rollBack);
}
