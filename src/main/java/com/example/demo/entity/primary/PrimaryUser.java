package com.example.demo.entity.primary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
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
public class PrimaryUser {

    private static final long serialVersionUID = 1L;

    /**
     * userId
     */
    @Id
    private String userId;

    /**
     * userName
     */
    private String userName;

    /**
     * unitId 关联testUser
     */
    private String unitId;
}
