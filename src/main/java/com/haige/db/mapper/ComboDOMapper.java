package com.haige.db.mapper;


import com.haige.db.entity.ComboDO;

public interface ComboDOMapper {
    int deleteByPrimaryKey(Long comboId);

    int insert(ComboDO record);

    int insertSelective(ComboDO record);

    ComboDO selectByPrimaryKey(Long comboId);

    int updateByPrimaryKeySelective(ComboDO record);

    int updateByPrimaryKey(ComboDO record);
}