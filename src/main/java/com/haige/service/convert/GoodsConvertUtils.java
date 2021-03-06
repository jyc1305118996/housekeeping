package com.haige.service.convert;

import com.haige.db.entity.GoodsInfoDO;
import com.haige.service.dto.CreateGoodsDTO;
import com.haige.service.dto.GoodsInfoDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Archie
 * @date 2019/12/28 14:46
 */
public class GoodsConvertUtils {
    public  static GoodsInfoDO convert(GoodsInfoDTO goodsInfoDTO){
        GoodsInfoDO goodsInfoDO = new GoodsInfoDO();
        BeanUtils.copyProperties(goodsInfoDTO, goodsInfoDO);
        goodsInfoDO.setGoodsUpdateTime(new Date());
        return goodsInfoDO;
    }
    public  static GoodsInfoDTO convert(GoodsInfoDO goodsInfoDO){
        GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();
        BeanUtils.copyProperties(goodsInfoDO, goodsInfoDTO);
        return goodsInfoDTO;
    }

    public  static GoodsInfoDO convert(CreateGoodsDTO createGoodsDTO){
        GoodsInfoDO goodsInfoDO = new GoodsInfoDO();
        BeanUtils.copyProperties(createGoodsDTO, goodsInfoDO);
        goodsInfoDO.setGoodsCreateTime(new Date());
        return goodsInfoDO;
    }
}
