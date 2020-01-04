package com.haige.db.mapper;


import com.haige.db.entity.CouponDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponDOMapper {
    int deleteByPrimaryKey(Integer ucId);

    int insert(CouponDO record);

    int insertSelective(CouponDO record);

    CouponDO selectByPrimaryKey(Integer ucId);

    int updateByPrimaryKeySelective(CouponDO record);

    int updateByPrimaryKey(CouponDO record);
}