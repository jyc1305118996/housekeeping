package com.haige.web.controller;

import com.haige.auth.annotation.Permission;
import com.haige.auth.enums.PermissionType;
import com.haige.common.bean.ResultInfo;
import com.haige.db.entity.GoodsInfoDO;
import com.haige.service.GoodsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    public Mono<ResultInfo<List<GoodsInfoDO>>> queryGoodsInfoList() {

  
        return goodsInfoService.goodsInfoList();
    }


    @GetMapping(value = "/queryGoodsInfoByid/{goodid}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Permission(PermissionType.ALL)
    public Mono<ResultInfo<GoodsInfoDO>> queryGoodsid(@PathVariable Integer goodid) {


        return goodsInfoService.goodsInfoById(goodid);
    }




}
