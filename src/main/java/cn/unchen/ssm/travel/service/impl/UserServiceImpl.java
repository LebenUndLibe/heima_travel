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
        String content = "<a href='http://localhost/travel/activeUserServlet?code="+user.getCode()+"'>点击激活账号【黑马旅游网】</a>";

        try {
            MailUtils.sendMail(user.getEmail(),content,"【黑马旅游网】账号激活邮件");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultInfo;


    }
}
