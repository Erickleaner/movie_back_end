package cn.itcast.movie_back_end.service;

import cn.itcast.movie_back_end.domain.dto.UserQuery;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.domain.vo.PageGP;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    User getByUserNameAndPwd(User user);

    boolean usernameExist(String userName);

    boolean phoneExist(String phone);

    PageGP<User> searchByQuery(UserQuery userQuery);

    boolean insert(User user);

    boolean update(User user);
}
