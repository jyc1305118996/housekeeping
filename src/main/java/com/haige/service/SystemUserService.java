package com.haige.service;



import com.haige.common.bean.ResultInfo;
import com.haige.web.vo.PhoneLoginVO;
import com.haige.web.vo.UserBaseVO;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author : Aaron
 * create at:  2019-10-17  10:58
 * @description: 系统用户返回类
 */
@Service
public interface SystemUserService {

   
   /**
   * @description: 手机号验证码登陆
   * @param: 
   * @return: 
   * @author: huxianming
   * @date: 2019-10-20
   */
   Mono<ResultInfo<UserBaseVO>> loginByPhoneAndCode(ServerWebExchange exchange, Mono<PhoneLoginVO> phone);
}
