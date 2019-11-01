package com.haige.db.mapper;


import com.haige.db.entity.UserBaseDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
public interface UserBaseDOMapper {
    int deleteByPrimaryKey(BigDecimal ubdId);

    int insert(UserBaseDO record);

    int insertSelective(UserBaseDO record);

    UserBaseDO selectByPrimaryKey(BigDecimal ubdId);

    int updateByPrimaryKeySelective(UserBaseDO record);

    int updateByPrimaryKey(UserBaseDO record);

    UserBaseDO findByToken(@Param("token") String token);
}