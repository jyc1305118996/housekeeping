package com.haige.db.mapper;


import com.haige.db.entity.GoodsInfoDO;

public interface GoodsInfoDOMapper {
    int deleteByPrimaryKey(Integer goodsId);

    int insert(GoodsInfoDO record);

    int insertSelective(GoodsInfoDO record);

    GoodsInfoDO selectByPrimaryKey(Integer goodsId);

    int updateByPrimaryKeySelective(GoodsInfoDO record);

    int updateByPrimaryKey(GoodsInfoDO record);
}