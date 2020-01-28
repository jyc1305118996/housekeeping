package com.haige.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCodeEnum;
import com.haige.convert.ConvertUtils;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.db.mapper.GoodsInfoDOMapper;
import com.haige.service.GoodsInfoService;
import com.haige.service.convert.GoodsConvertUtils;
import com.haige.service.dto.CreateGoodsDTO;
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
    public Mono<ResultInfo<List<GoodsInfoDO>>> goodsInfoList(String status,String type) {
        List<GoodsInfoDO> goodsInfoDoList = goodsInfoDOMapper.findGoodsInfoDoList(status,type);
        ResultInfo<List<GoodsInfoDO>> result = new ResultInfo<List<GoodsInfoDO>>();
        result.setData(goodsInfoDoList);
        result.setCount(Long.valueOf(goodsInfoDoList.size()));
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
                .doOnNext(goodsInfoDO -> goodsInfoDOMapper.updateByPrimaryKeySelective(goodsInfoDO))
                .map(goodsInfoDO -> ResultInfo.buildSuccess("success"));
    }

    @Override
    public Mono<ResultInfo> saveGoods(Mono<CreateGoodsDTO> mono) {
        return mono.map(GoodsConvertUtils::convert)
                .doOnNext(goodsInfoDO -> goodsInfoDOMapper.insertSelective(goodsInfoDO))
                .map(goodsInfoDO -> ResultInfo.buildSuccess("success"));
    }

    @Override
    public Mono<ResultInfo> isDel(int id, String status) {
        return Mono.just(id)
                .map(id1 -> goodsInfoDOMapper.selectByPrimaryKey(id1))
                .doOnNext(goodsInfoDO -> {
                    goodsInfoDO.setGoodsIsDel(status);
                    goodsInfoDOMapper.updateByPrimaryKeySelective(goodsInfoDO);
                })
                .map(i -> ResultInfo.buildSuccess("success"));
    }

    @Override
    public Mono<ResultInfo> webQueryGoodsInfoList(int index, int size, String goodType) {
        return Mono.just(PageHelper.startPage(index, size))
                .map(page -> goodsInfoDOMapper.findAll(goodType))
                .map(PageInfo::new)
                .map(goodsInfoDOPageInfo -> {
                    List<GoodsInfoDTO> convert = ConvertUtils.convert(goodsInfoDOPageInfo.getList(), GoodsConvertUtils::convert);
                    ResultInfo<List<GoodsInfoDTO>> listResultInfo = ResultInfo.buildSuccess(convert);
                    listResultInfo.setCount(goodsInfoDOPageInfo.getTotal());
                    return listResultInfo;
                });
    }
}

