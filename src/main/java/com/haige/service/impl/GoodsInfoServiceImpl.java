package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCode;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.db.mapper.GoodsInfoDOMapper;
import com.haige.service.GoodsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author : Aaron
 * create at:  2019-11-06  23:33
 * @description: 套餐表
 */
@Service
@Slf4j

public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    private GoodsInfoDOMapper goodsInfoDOMapper;

    @Override
    public Mono<ResultInfo<List<GoodsInfoDO>>> goodsInfoList() {
        List<GoodsInfoDO> goodsInfoDoList = goodsInfoDOMapper.findGoodsInfoDoList();
        ResultInfo<List<GoodsInfoDO>> result = new ResultInfo<List<GoodsInfoDO>>();
        result.setData(goodsInfoDoList);
        result.setCount(String.valueOf(goodsInfoDoList.size()));
        result.setCode(StatusCode.OK.getCode());
        result.setMessage(StatusCode.OK.getValue());
        return Mono.justOrEmpty(result);
    }
}
