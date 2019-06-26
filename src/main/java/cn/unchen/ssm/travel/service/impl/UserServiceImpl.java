package cn.unchen.ssm.travel.service.impl;

import cn.unchen.ssm.travel.dao.UserDao;
import cn.unchen.ssm.travel.domain.ResultInfo;
import cn.unchen.ssm.travel.domain.User;
import cn.unchen.ssm.travel.service.UserService;
import cn.unchen.ssm.travel.utils.MailUtils;
import cn.unchen.ssm.travel.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname UserServiceImpl
 * @Description 实现类
 * @Date 2019/6/26 9:27
 * @Author chen jun
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public ResultInfo save(User user) {

        //判断用户名是否已存在
        User byUsername = userDao.findByUsername(user.getUsername());

        ResultInfo resultInfo = new ResultInfo();   //服务器相应信息对象
        if (byUsername != null) {
            resultInfo.setErrorMsg("注册失败,用户名已存在");
            resultInfo.setFlag(false);
            return resultInfo;
        }
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        userDao.save(user);
        //判断是否注册成功
        if (user.getUid() == null) {
            resultInfo.setErrorMsg("注册失败,请重试或联系管理员");
            resultInfo.setFlag(false);
            return resultInfo;
        }
        //注册成功发送激活邮件
        resultInfo.setFlag(true);
        String content = "<a href='http://localhost/travel/activeUserServlet?code=" + user.getCode() + "'>点击激活账号【黑马旅游网】</a>";

        try {
            MailUtils.sendMail(user.getEmail(), content, "【黑马旅游网】账号激活邮件");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultInfo;


    }

    public ResultInfo login(User user) {

        ResultInfo<User> resultInfo = new ResultInfo<User>();   //服务器相应信息对象
        User loginUser = userDao.findByUsername(user.getUsername());
        if (loginUser == null || !user.getPassword().equals(loginUser.getPassword())) {
//            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
            return resultInfo;
        }
        if ("N".equals(loginUser.getStatus())) {
//            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户尚未激活,请登录邮箱激活");
        }
        if ("Y".equals(loginUser.getStatus())) {
            resultInfo.setFlag(true);
            resultInfo.setData(loginUser);
        }
        return resultInfo;
    }

    public ResultInfo activeUser(String code) {
        ResultInfo resultInfo = new ResultInfo();
        if (code == null || "".equals(code)||code.length()!=32) {
            resultInfo.setErrorMsg("非法链接");
            return resultInfo;
        }
        User user = userDao.findByCode(code);
        if (user == null||"Y".equals(user.getStatus())) {
            resultInfo.setErrorMsg("激活码错误,或已失效");
            return resultInfo;
        }
        userDao.updateStatus(user);
        resultInfo.setFlag(true);
        return resultInfo;
    }
}
