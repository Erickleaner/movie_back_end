package cn.itcast.movie_back_end.other;

import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.domain.vo.CommonInfo;

public class InfoMaker {

    public static CommonInfo AdminInfo(User user){
        CommonInfo commonInfo = new CommonInfo();
        commonInfo.setName(user.getUserName());
        commonInfo.setRole(user.getRole());
        commonInfo.setIntroduction("I am a super administrator");
        commonInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return commonInfo;
    }

    public static CommonInfo UserInfo(User user){
        CommonInfo commonInfo = new CommonInfo();
        commonInfo.setName(user.getUserName());
        commonInfo.setRole(user.getRole());
        commonInfo.setIntroduction("I am a normal user");
        commonInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return commonInfo;
    }
}
