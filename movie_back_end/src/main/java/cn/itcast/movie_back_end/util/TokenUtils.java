package cn.itcast.movie_back_end.util;

import cn.itcast.movie_back_end.dao.UserDao;
import cn.itcast.movie_back_end.domain.pojo.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class TokenUtils {
    private static UserDao staticUserDao;
    @Autowired
    UserDao userDao;

    @PostConstruct
    public void setUserService(){
        staticUserDao = userDao;
    }

    public static String getToken(String userId,String sign){
        return JWT.create().withAudience(userId)//userId保存至token中
                .withExpiresAt(DateUtils.addHours(new Date(),2))//2h后过期
                .sign(Algorithm.HMAC256(sign));
    }
    public static User getCurrentUser(String token){
        try {
            if (StringUtils.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserDao.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }
}
