package com.haige.db.mapper;


import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SystemUserMapper {
    /**
     * 插入登陆日志
     * @return
     */
    int saveLoginLog(Map<String,String> param);
}