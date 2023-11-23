package cn.itcast.movie_back_end.service.Impl;

import cn.itcast.movie_back_end.dao.CountryDao;
import cn.itcast.movie_back_end.domain.pojo.Country;
import cn.itcast.movie_back_end.service.CountryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl extends ServiceImpl<CountryDao, Country>
        implements CountryService {
}
