package cn.itcast.movie_back_end.other;

import cn.itcast.movie_back_end.domain.vo.AdminInfo;

public class InfoMaker {

    public static AdminInfo AdminInfo(){
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setRoles(new String[]{"admin"});
        adminInfo.setIntroduction("I am a super administrator");
        adminInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        adminInfo.setName("Super Admin");
        return adminInfo;
    }
}
