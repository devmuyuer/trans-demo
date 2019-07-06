package com.example.demo.service.primary;

import com.example.demo.common.R;
import com.example.demo.entity.primary.PrimaryUser;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface PrimaryUserService {


    /**
     * 保存数据
     * @param user
     * @return
     */
    R save(PrimaryUser user);

    /**
     * 批量保存
     * @param unitId
     * @param rollBack
     * @return
     */
    R bathSave(String unitId, Boolean rollBack);
}
