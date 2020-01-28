package com.haige.web.controller;

import com.haige.auth.annotation.Permission;
import com.haige.auth.enums.PermissionType;
import com.haige.common.bean.ResultInfo;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.service.GoodsInfoService;
import com.haige.web.convert.GoodsConvertUtils;
import com.haige.web.request.CreateGoodsRequest;
import com.haige.web.request.UpdateGoodsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

/**
 * @author aaron
 * @date 2019/11/6 22:36
 * desc 套餐
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {


    @Autowired
    private GoodsInfoService goodsInfoService;


    /**
     * 测试参数校验框架
     *
     * @return
     */

    @GetMapping(value = "/queryGoodsInfoList",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Permission(PermissionType.ALL)
    public Mono<ResultInfo<List<GoodsInfoDO>>> queryGoodsInfoList(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "type", required = false) String type) {


        if (null == status) {

            status = "1";
        }

        return goodsInfoService.goodsInfoList(status, type);
    }


    @GetMapping(value = "/queryGoodsInfoByid/{goodid}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Permission(PermissionType.ALL)
    public Mono<ResultInfo<GoodsInfoDO>> queryGoodsid(@PathVariable Integer goodid) {


        return goodsInfoService.goodsInfoById(goodid);
    }


    @PutMapping(value = "/updateGoodsInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> update(@RequestBody @Valid Mono<UpdateGoodsRequest> mono) {
        return goodsInfoService.update(mono.map(GoodsConvertUtils::convert));
    }

    @DeleteMapping(value = "/isDel/{id}/{status}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> delete(@PathVariable("id") int id, @PathVariable("status") String status) {
        return goodsInfoService.isDel(id, status);
    }


    @GetMapping(value = "/web/queryGoodsInfoList",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Permission(PermissionType.ALL)
    public Mono<ResultInfo> webQueryGoodsInfoList(@RequestParam("index") int index, @RequestParam("size") int size, @RequestParam(value = "goodsType", required = false) String goodType) {


        return goodsInfoService.webQueryGoodsInfoList(index, size, goodType);
    }

    @PostMapping(value = "/createGoodsInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> saveGoods(@RequestBody @Valid Mono<CreateGoodsRequest> createGoodsRequest) {
        return goodsInfoService.saveGoods(createGoodsRequest.map(GoodsConvertUtils::convert));
    }
}
