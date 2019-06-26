package cn.unchen.ssm.travel.controller;

import cn.unchen.ssm.travel.domain.ResultInfo;
import cn.unchen.ssm.travel.domain.User;
import cn.unchen.ssm.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname UserController
 * @Description 用户控制器
 * @Date 2019/6/25 20:59
 * @Author chen jun
 */
@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public ResultInfo register(User user) {

        ResultInfo resultInfo = userService.save(user);

        return resultInfo;
    }
}
