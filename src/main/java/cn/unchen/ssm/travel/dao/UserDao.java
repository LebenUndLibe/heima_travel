package cn.unchen.ssm.travel.dao;

import cn.unchen.ssm.travel.domain.User;

/**
 * @Classname UserDao
 * @Description 用户Dao接口
 * @Date 2019/6/25 21:00
 * @Author chen jun
 */

public interface UserDao {
    /**
     * 新增用户
     * @param user
     * @return
     */
    void save(User user);

    /**
     * 根据用户名查询用户
     * @return
     */
    User findByUsername(String username);


    User findByCode(String code);

    void updateStatus(User user);
}
