package cn.unchen.ssm.travel.controller;

import cn.unchen.ssm.travel.domain.ResultInfo;
import cn.unchen.ssm.travel.domain.User;
import cn.unchen.ssm.travel.service.UserService;
import cn.unchen.ssm.travel.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     *  用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultInfo register(User user) {

        ResultInfo resultInfo = userService.save(user);

        return resultInfo;
    }

    /**
     *  用户激活
     * @param code 激活码
     * @return
     */
    @RequestMapping("/active")
    public Object active(@RequestParam("code") String code) {

        ResultInfo resultInfo = userService.activeUser(code);

        return resultInfo;
    }


    /**
     *  用户登录
     *
     * @param user      表单提交用户信息
     * @param request   请求对象
     * @param response  相应对象
     * @param autoLogin 是否自动登录
     * @return 响应信息对象
     */
    @RequestMapping("login")
    public ResultInfo login(User user, HttpServletRequest request, HttpServletResponse response, String autoLogin) {
        ResultInfo resultInfo = new ResultInfo();
        //尝试从session中获取User对象
        Object o = request.getSession().getAttribute("user");
        if (o != null) {
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("你已登录,请不要重复登录");
            return resultInfo;
        }
        //执行登录
        resultInfo = userService.login(user);

        //判断是否登录成功
        if (!resultInfo.isFlag()) {
            return resultInfo;
        }
        //添加已登录用户到session域
        User loginUser = (User) resultInfo.getData();
        request.getSession().setAttribute("user", loginUser);
        if ("on".equals(autoLogin)) {
            //勾选自动登录添加cookie
            CookieUtils.addCookie("username", loginUser.getUsername(), 60 * 60 * 24 * 7, response);
            CookieUtils.addCookie("password", loginUser.getPassword(), 60 * 60 * 24 * 7, response);

        } else {
            // System.out.println("删除cookie");
            CookieUtils.deleteCookie("username", null, 0, response);
            CookieUtils.deleteCookie("password", null, 0, response);
        }
        return resultInfo;
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return 登录用户
     */
    @RequestMapping(value = "/findLoginUser", method = RequestMethod.POST)
    public Object findLoginUser(HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }

}
