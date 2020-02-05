package com.haige.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.haige.web.request.RelationImageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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
    public Mono<ResultInfo> goodsInfoList(String status,String type) {
        List<GoodsInfoDO> goodsInfoDoList = goodsInfoDOMapper.findGoodsInfoDoList(status,type);
        List<GoodsInfoDTO> collect = goodsInfoDoList.stream().map(goodsInfoDO -> {
            String urls = goodsInfoDO.getGoodsCoverUrl();
            GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();
            BeanUtils.copyProperties(goodsInfoDO, goodsInfoDTO);
            goodsInfoDTO.setGoodsCoverUrl(null);
            goodsInfoDTO.setUrls(JSON.parse(urls));
            return goodsInfoDTO;
        })
                .collect(Collectors.toList());
        ResultInfo<List> result = new ResultInfo<>();
        result.setData(collect);
        result.setCount(Long.valueOf(goodsInfoDoList.size()));
        result.setCode(StatusCodeEnum.OK.getCode());
        result.setMessage(StatusCodeEnum.OK.getValue());
        return Mono.justOrEmpty(result);
    }

    @Override
    public Mono<ResultInfo> goodsInfoById(Integer goodsid) {
        GoodsInfoDO goodsInfoDo = goodsInfoDOMapper.selectByPrimaryKey(goodsid);
        ResultInfo<GoodsInfoDTO> result = new ResultInfo<GoodsInfoDTO>();
        GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();
        BeanUtils.copyProperties(goodsInfoDo, goodsInfoDTO);
        goodsInfoDTO.setGoodsCoverUrl(null);
        goodsInfoDTO.setUrls(JSON.parse(goodsInfoDo.getGoodsCoverUrl()));
        result.setData(goodsInfoDTO);
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

    @Override
    public Mono<ResultInfo> relationImage(Mono<RelationImageRequest> relationImageRequest) {
        return relationImageRequest.map(relationImageRequest1 -> {
            GoodsInfoDO goodsInfoDO = goodsInfoDOMapper.selectByPrimaryKey(relationImageRequest1.getGoodsId());
            goodsInfoDO.setGoodsCoverUrl(relationImageRequest1.getGoodsCoverUrl());
            return goodsInfoDOMapper.updateByPrimaryKeySelective(goodsInfoDO);
        })
                .map(i -> ResultInfo.buildSuccess("success"));
    }
}

