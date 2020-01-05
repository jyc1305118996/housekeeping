package com.haige.db.mapper;


import com.haige.db.entity.CouponDO;
import com.haige.service.dto.UserBaseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponDOMapper {
    int deleteByPrimaryKey(Integer ucId);

    int insert(CouponDO record);

    int insertSelective(CouponDO record);

    CouponDO selectByPrimaryKey(Integer ucId);

    int updateByPrimaryKeySelective(CouponDO record);

    int updateByPrimaryKey(CouponDO record);


    List<CouponDO> findUserCouponList(@Param("userId") Integer userId);

}