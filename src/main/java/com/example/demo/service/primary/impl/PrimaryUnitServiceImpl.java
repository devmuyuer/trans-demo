package com.example.demo.service.primary.impl;

import com.example.demo.common.R;
import com.example.demo.enums.REnum;
import com.example.demo.common.RUtil;
import com.example.demo.common.SystemException;
import com.example.demo.entity.primary.PrimaryUnit;
import com.example.demo.repository.primary.PrimaryUnitRepository;
import com.example.demo.service.primary.PrimaryUnitService;
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
public class PrimaryUnitServiceImpl implements PrimaryUnitService {

    @Autowired
    PrimaryUnitRepository primaryUnitRepository;
    @Autowired
    PrimaryUserService primaryUserService;


    /**
     * 新增
     *
     * @param unit
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public R save(PrimaryUnit unit) {
        PrimaryUnit unitSave = primaryUnitRepository.save(unit);
        log.info("单位信息保存：unitSave = " + unitSave);
        return RUtil.success("");
    }

    @Override
    @Transactional(value = "PRIMARY_PLATFORM_TX_MANAGER", propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public R bathSave(Boolean rollBack) {
        try {
            for (int i = 0; i < 4; i++) {

                PrimaryUnit unit = new PrimaryUnit();
                unit.setUnitId("00" + i);
                unit.setUnitName("单位" + i);
                primaryUserService.bathSave(unit.getUnitId(),rollBack);

                save(unit);
            }
            return RUtil.success("");
        } catch (SystemException e) {
            log.error("保存数据失败：msg: {}", e.getMessage());
            throw new SystemException(REnum.ERROR.getCode(), "保存数据失败 Error:" + e.getMessage());
        }
    }
}
