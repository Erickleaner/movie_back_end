package cn.itcast.movie_back_end.dao;


import cn.itcast.movie_back_end.MovieBackEndApplication;
import cn.itcast.movie_back_end.domain.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MovieBackEndApplication.class)
public class UserDaoTest {
    @Autowired
    UserDao userDao;
    @Test
    public void searchById() {
        User user = userDao.searchById(1);
        System.out.println(user.getUserName());
    }
}
