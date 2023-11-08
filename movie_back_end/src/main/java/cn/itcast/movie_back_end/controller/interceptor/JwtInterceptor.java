package cn.itcast.movie_back_end.controller.interceptor;

import cn.itcast.movie_back_end.util.Code;
import cn.itcast.movie_back_end.dao.UserDao;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.util.AuthAccess;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        if (StringUtils.isBlank(token)){
            token = request.getParameter("X-Token");
        }
        if (handler instanceof HandlerMethod){
            AuthAccess annotation = ((HandlerMethod)handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) return true;
        }
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            throw new BusinessException(Code.ILLEGAL_TOKEN_ERR,"token过期请先登录！");
        }

        User user = userDao.selectById(userId);
        if (user == null){
            throw new BusinessException(Code.ILLEGAL_TOKEN_ERR,"token过期请先登录！");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassWord())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e){
            throw new BusinessException(Code.ILLEGAL_TOKEN_ERR,"token过期请先登录！");
        }
        return true;
    }
}
