package com.haige.service;

import com.haige.db.entity.UserBaseDO;
import com.haige.db.mapper.UserBaseDOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Archie
 * @date 2020/1/4 15:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserBaseTest {
    @Autowired
    private UserBaseDOMapper userBaseDOMapper;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    public void updateUser(){
        UserBaseDO userBaseDO = new UserBaseDO();
        userBaseDO.setUbdId(1);
        userBaseDO.setUbdName("123456789");
        userBaseDO.setUbdPass(bCryptPasswordEncoder.encode("123456"));
        userBaseDOMapper.updateByPrimaryKeySelective(userBaseDO);
    }
}
