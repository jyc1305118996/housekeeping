package com.haige.db.mapper;


import com.haige.db.entity.ComboDO;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboDOMapper {
    int deleteByPrimaryKey(Long comboId);

    int insert(ComboDO record);

    int insertSelective(ComboDO record);

    ComboDO selectByPrimaryKey(Long comboId);

    int updateByPrimaryKeySelective(ComboDO record);

    int updateByPrimaryKeyWithBLOBs(ComboDO record);

    int updateByPrimaryKey(ComboDO record);
}