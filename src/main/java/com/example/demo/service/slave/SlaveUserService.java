package com.example.demo.service.slave;

import com.example.demo.common.R;
import com.example.demo.entity.slave.SlaveUser;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface SlaveUserService {


    /**
     * 保存数据
     * @param user
     * @return
     */
    R save(SlaveUser user);

    /**
     * 批量保存
     * @param unitId
     * @param rollBack
     * @return
     */
    R bathSave(String unitId, Boolean rollBack);
}
