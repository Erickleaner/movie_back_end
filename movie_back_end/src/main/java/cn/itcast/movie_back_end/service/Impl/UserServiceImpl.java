package cn.itcast.movie_back_end.service.Impl;

import cn.itcast.movie_back_end.dao.UserDao;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;


    @Override
    public User getByUserNameAndPwd(User user) {
        String userName = user.getUserName();
        String password = user.getPassWord();
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserName,userName);
        qw.eq(User::getPassWord,password);
        return userDao.selectOne(qw);
    }
}
