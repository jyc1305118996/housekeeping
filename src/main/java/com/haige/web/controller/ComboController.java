package com.haige.web.controller;

import com.haige.auth.annotation.Permission;
import com.haige.common.bean.ResultInfo;
import com.haige.common.enums.StatusCode;
import com.haige.service.ComboService;
import com.haige.web.vo.ComboVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Archie
 * @date 2019/10/20 17:25
 */
@RestController
@RequestMapping("/combo")
@Slf4j
public class ComboController {

    @Autowired
    private ComboService comboService;
    /**
     * 保存上传的商品
     * @param comboVO
     * @return
     */
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Permission()
    public Mono<ResultInfo<String>> saveCombo(@RequestBody @Valid Mono<ComboVO> comboVO) {
        return comboVO.map(comboVO1 -> {
            log.info("接受到前端的信息是: {}", comboVO1);
            return new ResultInfo<>(StatusCode.OK);
        });
    }
}
