package com.haige.db.mapper;


import com.haige.db.entity.OrderDO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface OrderDOMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);

    List<OrderDO> findOrderDoList(HashMap<String,String> param);



}