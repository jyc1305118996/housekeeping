package com.haige.db.mapper;


import com.haige.db.entity.CommontDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CommontDOMapper {
    int deleteByPrimaryKey(Long commontId);

    int insert(CommontDO record);

    int insertSelective(CommontDO record);

    CommontDO selectByPrimaryKey(Long commontId);

    int updateByPrimaryKeySelective(CommontDO record);

    int updateByPrimaryKey(CommontDO record);
}