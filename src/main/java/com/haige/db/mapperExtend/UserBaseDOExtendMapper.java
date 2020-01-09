package com.haige.db.mapperExtend;


import com.haige.db.entity.UserBaseDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBaseDOExtendMapper {
    /**
     * 查询管理员手机号
     * @return
     */
    String findByManagerIphone(@Param("orderId") String orderId);

    /**
     * 登陆
     * @param username
     * @return
     */
    UserBaseDO login(@Param("username") String username);

    List<UserBaseDO> queryEmployees();

}