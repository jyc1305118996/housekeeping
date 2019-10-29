package com.haige.db.mapper;


import com.haige.db.entity.LoginLogDO;

public interface LoginLogDOMapper {
    int deleteByPrimaryKey(Long sllId);

    int insert(LoginLogDO record);

    int insertSelective(LoginLogDO record);

    LoginLogDO selectByPrimaryKey(Long sllId);

    int updateByPrimaryKeySelective(LoginLogDO record);

    int updateByPrimaryKey(LoginLogDO record);
}