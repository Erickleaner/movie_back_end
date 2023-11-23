package cn.itcast.movie_back_end.service.Impl;

import cn.itcast.movie_back_end.dao.TypeDao;
import cn.itcast.movie_back_end.dao.UserDao;
import cn.itcast.movie_back_end.domain.pojo.Type;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.service.TypeService;
import cn.itcast.movie_back_end.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type>
        implements TypeService {
}
