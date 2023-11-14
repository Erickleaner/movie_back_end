package cn.itcast.movie_back_end.service.Impl;

import cn.itcast.movie_back_end.dao.MovieSubTypeDao;
import cn.itcast.movie_back_end.dao.TypeDao;
import cn.itcast.movie_back_end.domain.pojo.MovieSubType;
import cn.itcast.movie_back_end.domain.pojo.Type;
import cn.itcast.movie_back_end.service.MovieSubCountryService;
import cn.itcast.movie_back_end.service.MovieSubTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieSubTypeServiceImpl implements MovieSubTypeService {
    @Autowired
    MovieSubTypeDao movieSubTypeDao;

    @Autowired
    TypeDao typeDao;

    @Override
    public List<Type> getTypesByMovieId(int movieId) {
        LambdaQueryWrapper<MovieSubType> qw = new LambdaQueryWrapper<>();
        qw.eq(MovieSubType::getMovieId,movieId);
        List<MovieSubType> movieSubTypes = movieSubTypeDao.selectList(qw);
        List<Type> types = new ArrayList<>();
        for (MovieSubType movieSubType:movieSubTypes){
            int typeId = movieSubType.getTypeId();
            Type type = typeDao.selectById(typeId);
            types.add(type);
        }
        return types;
    }
}
