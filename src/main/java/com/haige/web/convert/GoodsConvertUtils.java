package com.haige.web.convert;

import com.haige.service.dto.GoodsInfoDTO;
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
}
