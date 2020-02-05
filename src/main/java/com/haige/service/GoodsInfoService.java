package com.haige.service;


import com.haige.common.bean.ResultInfo;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.service.dto.CreateGoodsDTO;
import com.haige.service.dto.GoodsInfoDTO;
import com.haige.web.request.RelationImageRequest;
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

    Mono<ResultInfo<List<GoodsInfoDO>>> goodsInfoList(String status,String type);


    /**
     * 根据id查询 具体套餐信息
     * @param goodsid
     * @return
     */
    Mono<ResultInfo<GoodsInfoDO>> goodsInfoById(Integer goodsid);

    Mono<ResultInfo> update(Mono<GoodsInfoDTO> mono);
    Mono<ResultInfo> saveGoods(Mono<CreateGoodsDTO> mono);

    Mono<ResultInfo> isDel(int id, String status);

    Mono<ResultInfo> webQueryGoodsInfoList(int index, int size, String goodType);


    Mono<ResultInfo> relationImage(Mono<RelationImageRequest> relationImageRequest);
}
