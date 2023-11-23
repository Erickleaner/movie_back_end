package cn.itcast.movie_back_end.dao;


import cn.itcast.movie_back_end.MovieBackEndApplication;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MovieBackEndApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    public void searchById() {
        User user = new User();
        user.setUserId(0);
        user.setUserName("user4");
        user.setPassWord("111111");
        userService.save(user);
        System.out.println(user.getUserId());
    }
}
