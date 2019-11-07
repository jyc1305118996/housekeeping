package com.haige.service;


import com.haige.common.bean.ResultInfo;
import com.haige.db.entity.GoodsInfoDO;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author aaron
 * create at:  2019-11-06  23:33
 */
public interface GoodsInfoService {

    /**
     * c查询所有的套餐
     * @return
     */

    Mono<ResultInfo<List<GoodsInfoDO>>> goodsInfoList();
}
