package com.example.demo.service.slave.impl;

import com.example.demo.common.R;
import com.example.demo.enums.REnum;
import com.example.demo.common.RUtil;
import com.example.demo.common.SystemException;
import com.example.demo.entity.slave.SlaveUnit;
import com.example.demo.repository.slave.SlaveUnitRepository;
import com.example.demo.service.slave.SlaveUnitService;
import com.example.demo.service.slave.SlaveUserService;
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
public class SlaveUnitServiceImpl implements SlaveUnitService {

    @Autowired
    SlaveUnitRepository slaveUnitRepository ;
    @Autowired
    SlaveUserService slaveUserService;


    /**
     * 新增
     *
     * @param unit
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public R save(SlaveUnit unit) {
        SlaveUnit unitSave = slaveUnitRepository.save(unit);
        log.info("单位信息保存：unitSave = {}", unitSave);
        return RUtil.success("");
    }

    @Override
    @Transactional(value = "SLAVE_PLATFORM_TX_MANAGER", propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public R bathSave(Boolean rollBack) {
        try {
            for (int i = 0; i < 4; i++) {

                SlaveUnit unit = new SlaveUnit();
                unit.setUnitId("00" + i);
                unit.setUnitName("单位" + i);
                slaveUserService.bathSave(unit.getUnitId(),rollBack);

                save(unit);
            }
            return RUtil.success("");
        } catch (SystemException e) {
            log.error("保存数据失败：msg: {}", e.getMessage());
            throw new SystemException(REnum.ERROR.getCode(), "保存数据失败 Error:" + e.getMessage());
        }
    }
}
