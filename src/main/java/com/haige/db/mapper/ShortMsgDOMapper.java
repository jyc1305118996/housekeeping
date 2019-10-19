package com.haige.db.mapper;


import com.haige.db.entity.ShortMsgDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortMsgDOMapper {
    int deleteByPrimaryKey(Long smiMesaggeId);

    int insert(ShortMsgDO record);

    int insertSelective(ShortMsgDO record);

    ShortMsgDO selectByPrimaryKey(Long smiMesaggeId);

    int updateByPrimaryKeySelective(ShortMsgDO record);

    int updateByPrimaryKey(ShortMsgDO record);

    List<ShortMsgDO> findByIphone(@Param("iphone") String iphone, @Param("date")String date);

    List<ShortMsgDO> findByIp(@Param("ip")String ip, @Param("date")String date);

    ShortMsgDO findList(@Param("ip")String ip, @Param("iphone")String iphone);
}