package com.haige.db.mapper;


import com.haige.db.entity.ServeDetailDO;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ServeDetailDOMapper {
    int deleteByPrimaryKey(Integer serveId);

    int insert(ServeDetailDO record);

    int insertSelective(ServeDetailDO record);

    ServeDetailDO selectByPrimaryKey(Integer serveId);

    int updateByPrimaryKeySelective(ServeDetailDO record);

    int updateByPrimaryKey(ServeDetailDO record);

    List<HashMap<String,String>> findServeDetailDOList(HashMap<String,String> param);

}