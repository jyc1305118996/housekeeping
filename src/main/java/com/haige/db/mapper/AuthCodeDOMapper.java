package com.haige.db.mapper;


import com.haige.db.entity.AuthCodeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthCodeDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthCodeDO record);

    int insertSelective(AuthCodeDO record);

    AuthCodeDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthCodeDO record);

    int updateByPrimaryKey(AuthCodeDO record);

    List<AuthCodeDO> findByIphone(@Param("iphone") String iphone, @Param("date") String date);

    List<AuthCodeDO> findByIp(@Param("ip") String ip, @Param("date") String date);
}