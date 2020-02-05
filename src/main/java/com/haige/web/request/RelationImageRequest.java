package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2020/2/5 15:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationImageRequest {
    @NotNull
    private Integer goodsId;
    @NotNull
    private String goodsCoverUrl;
}
