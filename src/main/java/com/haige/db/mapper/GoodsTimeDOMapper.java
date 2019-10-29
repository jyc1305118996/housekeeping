package com.haige.db.mapper;


import com.haige.db.entity.GoodsTimeDO;

public interface GoodsTimeDOMapper {
    int deleteByPrimaryKey(Integer gtId);

    int insert(GoodsTimeDO record);

    int insertSelective(GoodsTimeDO record);

    GoodsTimeDO selectByPrimaryKey(Integer gtId);

    int updateByPrimaryKeySelective(GoodsTimeDO record);

    int updateByPrimaryKey(GoodsTimeDO record);
}