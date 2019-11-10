package com.haige.db.mapper;


import com.haige.db.entity.GoodsCouponDO;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsCouponDOMapper {
    int deleteByPrimaryKey(Integer gcId);

    int insert(GoodsCouponDO record);

    int insertSelective(GoodsCouponDO record);

    GoodsCouponDO selectByPrimaryKey(Integer gcId);

    int updateByPrimaryKeySelective(GoodsCouponDO record);

    int updateByPrimaryKey(GoodsCouponDO record);
}