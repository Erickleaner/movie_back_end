package cn.itcast.movie_back_end.controller;


import cn.itcast.movie_back_end.domain.dto.LoginDto;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.domain.vo.AdminInfo;
import cn.itcast.movie_back_end.domain.vo.LoginToken;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.other.InfoMaker;
import cn.itcast.movie_back_end.service.UserService;
import cn.itcast.movie_back_end.util.AuthAccess;
import cn.itcast.movie_back_end.util.Code;
import cn.itcast.movie_back_end.util.Result;
import cn.itcast.movie_back_end.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //通过token获取info不需要拦截
    @AuthAccess
    @GetMapping("/info")
    public Result info(String token){
        User currentUser = TokenUtils.getCurrentUser(token);
        if (currentUser != null){
            AdminInfo adminInfo = InfoMaker.AdminInfo();
            return new Result(Code.REQUEST_OK,adminInfo);
        }else {
            return new Result(Code.REQUEST_OK,null);
        }
    }


    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        User dbUser = userService.getByUserNameAndPwd(user);

        if (dbUser == null){
            throw new BusinessException(Code.LOGIN_FAULT,"用户名或密码错误，登录失败！");
        }else {
            String role = dbUser.getRole();
            List<String> roles = Arrays.asList(role);
            String token = TokenUtils.getToken(dbUser.getUserId().toString(),dbUser.getPassWord());
            return new Result(Code.REQUEST_OK,new LoginToken(true,token,roles));
        }
    }
}
