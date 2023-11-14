package cn.itcast.movie_back_end.service;

import cn.itcast.movie_back_end.domain.pojo.Type;

import java.util.List;

public interface MovieSubTypeService {
    List<Type> getTypesByMovieId(int movieId);
}
