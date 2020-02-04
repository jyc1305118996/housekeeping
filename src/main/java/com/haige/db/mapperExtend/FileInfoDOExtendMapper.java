package com.haige.db.mapperExtend;


import com.haige.db.entity.FileInfoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileInfoDOExtendMapper {



    List<FileInfoDO> findByType(@Param("type") String type);


}