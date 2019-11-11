package com.haige.db.mapper;


import com.haige.db.entity.OrderDO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDOMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);
}