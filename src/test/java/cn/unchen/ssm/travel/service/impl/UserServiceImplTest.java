package cn.unchen.ssm.travel.service.impl;

import cn.unchen.ssm.travel.domain.ResultInfo;
import cn.unchen.ssm.travel.domain.User;
import cn.unchen.ssm.travel.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Classname UserServiceImplTest
 * @Description TODO
 * @Date 2019/6/26 9:43
 * @Author chen jun
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class UserServiceImplTest {


    @Autowired
    UserService userService;
    @Test
    public void save() {
        User user = new User();
        user.setUsername("chenjun1");
        user.setPassword("123");
        user.setName("陈俊");
        user.setSex("男");
        user.setEmail("admin@xinlone.com");
        user.setBirthday("2011-1-1");
        ResultInfo resultInfo = userService.save(user);
        System.out.println(resultInfo.getErrorMsg()+"  "+resultInfo.isFlag());

    }
}