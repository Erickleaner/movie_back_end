package cn.itcast.movie_back_end.service.Impl;

import cn.itcast.movie_back_end.dao.UserDao;
import cn.itcast.movie_back_end.domain.dto.UserQuery;
import cn.itcast.movie_back_end.domain.pojo.Movie;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.domain.vo.MovieVo;
import cn.itcast.movie_back_end.domain.vo.PageGP;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.service.UserService;
import cn.itcast.movie_back_end.util.Code;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao , User>
        implements UserService {


    @Override
    public User getByUserNameAndPwd(User user) {
        String userName = user.getUserName();
        String password = user.getPassWord();
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserName,userName);
        qw.eq(User::getPassWord,password);
        return this.getOne(qw);
    }

    @Override
    public boolean usernameExist(String userName){
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserName,userName);
        return this.count(qw) == 1;
    }

    public boolean usernameUpdateValid(User user){
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserName,user.getUserName());
        qw.ne(User::getUserId,user.getUserId());
        return this.count(qw) == 0;
    }

    @Override
    public boolean phoneExist(String phone){
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getPhone,phone);
        return this.count(qw) == 1;
    }

    @Override
    public PageGP<User> searchByQuery(UserQuery userQuery) {
        int page = userQuery.getPage();
        int limit = userQuery.getLimit();
        String sort = userQuery.getSort();
        String searchName = userQuery.getUsername();
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getRole,"user");
        qw.like(User::getUserName,searchName);
        if (sort.equals("+id")) qw.orderByAsc(User::getUserId);
        if (sort.equals("-id")) qw.orderByDesc(User::getUserId);
        IPage<User> iPage = new Page<>(page,limit);
        this.page(iPage,qw);

        PageGP<User> pageGP = new PageGP<>();
        pageGP.setTotal(iPage.getTotal());
        pageGP.setItems(iPage.getRecords());
        return pageGP;
    }

    @Override
    public boolean insert(User user) {
        String userName = user.getUserName();
        boolean exist = usernameExist(userName);
        if (exist) throw new BusinessException(Code.INSERT_ERR,"用户名已存在，不可添加该用户！");
        user.setRole("user");
        return this.save(user);
    }

    @Override
    public boolean update(User user) {
        boolean valid = usernameUpdateValid(user);
        if (!valid) throw new BusinessException(Code.UPDATE_ERR,"用户名已存在，不可更新该用户！");
        return this.updateById(user);
    }

}
