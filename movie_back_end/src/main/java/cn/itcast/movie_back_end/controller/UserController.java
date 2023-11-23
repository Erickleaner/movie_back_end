package cn.itcast.movie_back_end.controller;


import cn.itcast.movie_back_end.domain.dto.LoginDto;
import cn.itcast.movie_back_end.domain.dto.RegisterDto;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.domain.vo.CommonInfo;
import cn.itcast.movie_back_end.domain.vo.LoginToken;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.other.InfoMaker;
import cn.itcast.movie_back_end.service.UserService;
import cn.itcast.movie_back_end.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
            CommonInfo info;
            if (currentUser.getRole().equals("admin"))
                info = InfoMaker.AdminInfo(currentUser);
            else
                info = InfoMaker.UserInfo(currentUser);
            return new Result(Code.REQUEST_OK, info);
        }else {
            return new Result(Code.REQUEST_OK,null);
        }
    }

    @AuthAccess
    @GetMapping("/checkUsername")
    public Result checkUsername(String username){
        boolean exist = userService.usernameExist(username);
        return new Result(Code.REQUEST_OK,!exist);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto, HttpSession session){
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        String phone = registerDto.getPhone();
        String code = registerDto.getVerifyCode();
        boolean exist;
        exist = userService.usernameExist(username);
        if (exist){
            throw new BusinessException(Code.BUSINESS_ERR,"用户名已存在！");
        }
        String saveCode = (String) session.getAttribute("code");
        String savePhone = (String) session.getAttribute("phone");
        if (saveCode == null || savePhone == null){
            throw new BusinessException(Code.BUSINESS_ERR,"请先发送验证码进行验证！");
        }
        if (!saveCode.equals(code) || !savePhone.equals(phone)){
            throw new BusinessException(Code.BUSINESS_ERR,"手机验证码有误！");
        }
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        user.setPhone(phone);
        user.setRole("user");
        boolean save = userService.save(user);
        session.removeAttribute("code");
        session.removeAttribute("phone");
        return new Result(Code.REQUEST_OK,save);
    }


    @AuthAccess
    @GetMapping("/verifyAsk")
    public Result verifyAsk(String phone, HttpSession session){
        boolean phoneExist = userService.phoneExist(phone);
        if (phoneExist){
            throw new BusinessException(Code.BUSINESS_ERR,"手机号码已存在!");
        }
        PhoneCodeVerify phoneCodeVerify = new PhoneCodeVerify();
        String code = phoneCodeVerify.sendSms(phone);
        if (code == null){
            throw new BusinessException(Code.BUSINESS_ERR,"发送验证码失败!");
        }
        session.setAttribute("code",code);
        session.setAttribute("phone",phone);
        return new Result(Code.REQUEST_OK,true);
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

            String token = TokenUtils.getToken(dbUser.getUserId().toString(),dbUser.getPassWord());
            return new Result(Code.REQUEST_OK,new LoginToken(true,token,role));
        }
    }
}
