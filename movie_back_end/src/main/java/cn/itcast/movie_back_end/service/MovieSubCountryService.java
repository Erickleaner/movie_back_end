package cn.itcast.movie_back_end.service;

import cn.itcast.movie_back_end.domain.pojo.Country;
import cn.itcast.movie_back_end.domain.pojo.MovieSubCountry;
import cn.itcast.movie_back_end.domain.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MovieSubCountryService extends IService<MovieSubCountry> {
    List<Country> getCountriesByMovieId(int movieId);
}
