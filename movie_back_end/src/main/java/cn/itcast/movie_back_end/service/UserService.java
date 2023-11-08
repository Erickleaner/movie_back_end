package cn.itcast.movie_back_end.service;

import cn.itcast.movie_back_end.domain.pojo.User;

public interface UserService {

    User getByUserNameAndPwd(User user);

}
