package com.example.demo.entity.slave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

/**
 * 用户
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SlaveUnit {

    private static final long serialVersionUID = 1L;

    /**
     * unitId
     */
    @Id
    private String unitId;

    /**
     * unitName
     */
    private String unitName;

}
