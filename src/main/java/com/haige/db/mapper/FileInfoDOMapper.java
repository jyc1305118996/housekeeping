package com.haige.db.mapper;


import com.haige.db.entity.FileInfoDO;

public interface FileInfoDOMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(FileInfoDO record);

    int insertSelective(FileInfoDO record);

    FileInfoDO selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(FileInfoDO record);

    int updateByPrimaryKey(FileInfoDO record);
}