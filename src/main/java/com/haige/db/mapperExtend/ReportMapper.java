package com.haige.db.mapperExtend;

import com.haige.db.entity.ReportMonthDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Archie
 * @date 2020/1/13 22:08
 */
@Repository
public interface ReportMapper {
    List<ReportMonthDO> month(@Param("month") String month);
}
