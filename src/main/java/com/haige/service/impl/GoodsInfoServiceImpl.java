package com.haige.service.impl;

import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCodeEnum;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.db.mapper.GoodsInfoDOMapper;
import com.haige.service.GoodsInfoService;
import com.haige.service.convert.GoodsConvertUtils;
import com.haige.service.dto.GoodsInfoDTO;
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
@SuppressWarnings("all")
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    private GoodsInfoDOMapper goodsInfoDOMapper;

    @Override
    public Mono<ResultInfo<List<GoodsInfoDO>>> goodsInfoList() {
        List<GoodsInfoDO> goodsInfoDoList = goodsInfoDOMapper.findGoodsInfoDoList();
        ResultInfo<List<GoodsInfoDO>> result = new ResultInfo<List<GoodsInfoDO>>();
        result.setData(goodsInfoDoList);
        result.setCount(String.valueOf(goodsInfoDoList.size()));
        result.setCode(StatusCodeEnum.OK.getCode());
        result.setMessage(StatusCodeEnum.OK.getValue());
        return Mono.justOrEmpty(result);
    }

    @Override
    public Mono<ResultInfo<GoodsInfoDO>> goodsInfoById(Integer goodsid) {
        GoodsInfoDO goodsInfoDo = goodsInfoDOMapper.selectByPrimaryKey(goodsid);
        ResultInfo<GoodsInfoDO> result = new ResultInfo<GoodsInfoDO>();
        result.setData(goodsInfoDo);
        result.setCode(StatusCodeEnum.OK.getCode());
        result.setMessage(StatusCodeEnum.OK.getValue());
        return Mono.justOrEmpty(result);
    }

    @Override
    public Mono<ResultInfo> update(Mono<GoodsInfoDTO> mono) {
        return mono.map(GoodsConvertUtils::convert)
                .doOnNext(goodsInfoDTO -> goodsInfoDOMapper.updateByPrimaryKeySelective(goodsInfoDTO))
                .map(goodsInfoDO -> ResultInfo.buildSuccess("success"));
    }
}

