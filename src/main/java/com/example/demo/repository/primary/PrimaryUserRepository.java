package com.example.demo.repository.primary;


import com.example.demo.entity.primary.PrimaryUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-02-25 09:10
 */
public interface PrimaryUserRepository extends JpaRepository<PrimaryUser, String> {


}
