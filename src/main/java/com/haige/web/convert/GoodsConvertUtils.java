package com.haige.web.convert;

import com.haige.service.dto.CreateGoodsDTO;
import com.haige.service.dto.GoodsInfoDTO;
import com.haige.web.request.CreateGoodsRequest;
import com.haige.web.request.UpdateGoodsRequest;
import org.springframework.beans.BeanUtils;

/**
 * @author Archie
 * @date 2019/12/28 14:43
 */
public class GoodsConvertUtils {
    public static GoodsInfoDTO convert(UpdateGoodsRequest goodsRequest){
        GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();
        BeanUtils.copyProperties(goodsRequest, goodsInfoDTO);
        return goodsInfoDTO;
    }
    public static CreateGoodsDTO convert(CreateGoodsRequest createGoodsRequest){
        CreateGoodsDTO goodsInfoDTO = new CreateGoodsDTO();
        BeanUtils.copyProperties(createGoodsRequest, goodsInfoDTO);
        return goodsInfoDTO;
    }
}
