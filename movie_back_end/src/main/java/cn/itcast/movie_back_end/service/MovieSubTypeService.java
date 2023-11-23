package cn.itcast.movie_back_end.service;

import cn.itcast.movie_back_end.domain.pojo.MovieSubType;
import cn.itcast.movie_back_end.domain.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MovieSubTypeService extends IService<MovieSubType> {
    List<Type> getTypesByMovieId(int movieId);
}
