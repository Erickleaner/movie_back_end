package cn.itcast.movie_back_end.service.Impl;

import cn.itcast.movie_back_end.dao.CountryDao;
import cn.itcast.movie_back_end.dao.MovieSubCountryDao;
import cn.itcast.movie_back_end.dao.MovieSubTypeDao;
import cn.itcast.movie_back_end.dao.TypeDao;
import cn.itcast.movie_back_end.domain.pojo.Country;
import cn.itcast.movie_back_end.domain.pojo.MovieSubCountry;
import cn.itcast.movie_back_end.domain.pojo.MovieSubType;
import cn.itcast.movie_back_end.domain.pojo.Type;
import cn.itcast.movie_back_end.service.MovieSubCountryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieSubCountryServiceImpl implements MovieSubCountryService {


    @Autowired
    MovieSubCountryDao movieSubCountryDao;

    @Autowired
    CountryDao countryDao;


    @Override
    public List<Country> getCountriesByMovieId(int movieId) {
        LambdaQueryWrapper<MovieSubCountry> qw = new LambdaQueryWrapper<>();
        qw.eq(MovieSubCountry::getMovieId,movieId);
        List<MovieSubCountry> movieSubCountries = movieSubCountryDao.selectList(qw);
        List<Country> countries = new ArrayList<>();
        for (MovieSubCountry movieSubCountry:movieSubCountries){
            int countryId = movieSubCountry.getCountryId();
            Country country = countryDao.selectById(countryId);
            countries.add(country);
        }
        return countries;
    }
}
