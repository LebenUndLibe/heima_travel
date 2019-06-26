package cn.unchen.ssm.travel.service;

import cn.unchen.ssm.travel.domain.ResultInfo;
import cn.unchen.ssm.travel.domain.User;

/**
 * @Classname UserService
 * @Description 用户Service接口
 * @Date 2019/6/25 20:58
 * @Author chen jun
 */
public interface UserService {
    /**
     * 新增用户
     * @param user
     * @return
     */
    ResultInfo save(User user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    ResultInfo login(User user);

    /**
     * 用户激活
     * @param code
     * @return
     */
    ResultInfo activeUser(String code);
}
