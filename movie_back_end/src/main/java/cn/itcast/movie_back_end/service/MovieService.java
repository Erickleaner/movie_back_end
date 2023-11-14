package cn.itcast.movie_back_end.service;

import cn.itcast.movie_back_end.domain.pojo.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> searchByTags(int countryId, int typeId,int yearId);
}
