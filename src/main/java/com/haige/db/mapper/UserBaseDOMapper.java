package com.haige.db.mapper;


import com.haige.db.entity.UserBaseDO;

import java.math.BigDecimal;

public interface UserBaseDOMapper {
    int deleteByPrimaryKey(BigDecimal ubdId);

    int insert(UserBaseDO record);

    int insertSelective(UserBaseDO record);

    UserBaseDO selectByPrimaryKey(BigDecimal ubdId);

    int updateByPrimaryKeySelective(UserBaseDO record);

    int updateByPrimaryKey(UserBaseDO record);
}