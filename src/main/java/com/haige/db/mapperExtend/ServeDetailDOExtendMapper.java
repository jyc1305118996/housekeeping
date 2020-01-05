package com.haige.db.mapperExtend;


import com.haige.db.entity.ServeDetailInfoDO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ServeDetailDOExtendMapper {

    ArrayList<ServeDetailInfoDO> findAll();

}