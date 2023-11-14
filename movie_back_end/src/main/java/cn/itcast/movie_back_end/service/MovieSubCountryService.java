package cn.itcast.movie_back_end.service;

import cn.itcast.movie_back_end.domain.pojo.Country;

import java.util.List;

public interface MovieSubCountryService {
    List<Country> getCountriesByMovieId(int movieId);
}
