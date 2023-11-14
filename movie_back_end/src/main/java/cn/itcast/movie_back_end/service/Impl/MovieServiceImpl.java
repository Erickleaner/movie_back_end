package cn.itcast.movie_back_end.service.Impl;

import cn.itcast.movie_back_end.dao.MovieDao;
import cn.itcast.movie_back_end.dao.MovieSubCountryDao;
import cn.itcast.movie_back_end.dao.MovieSubTypeDao;
import cn.itcast.movie_back_end.domain.pojo.Movie;
import cn.itcast.movie_back_end.domain.pojo.MovieSubCountry;
import cn.itcast.movie_back_end.domain.pojo.MovieSubType;
import cn.itcast.movie_back_end.service.MovieService;
import cn.itcast.movie_back_end.util.DataParse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieDao movieDao;

    @Autowired
    MovieSubCountryDao movieSubCountryDao;

    @Autowired
    MovieSubTypeDao movieSubTypeDao;

    private static List<Integer> findIntersection(List<MovieSubType> mstList, List<MovieSubCountry> mscList) {
        return mstList.stream()
                .filter(mst -> mscList.stream().anyMatch(msc -> msc.getMovieId() == mst.getMovieId()))
                .map(MovieSubType::getMovieId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> searchByTags(int countryId, int typeId, int yearId) {

        //movieSubType     -> movieId
        //movieSubCountry  -> movieId;
        LambdaQueryWrapper<MovieSubCountry> mscQw = new LambdaQueryWrapper<>();
        mscQw.orderByAsc(MovieSubCountry::getMovieId);
        if (countryId != 0) mscQw.eq(MovieSubCountry::getCountryId,countryId);
        List<MovieSubCountry> mscList = movieSubCountryDao.selectList(mscQw);

        LambdaQueryWrapper<MovieSubType> mstQw = new LambdaQueryWrapper<>();
        mstQw.orderByAsc(MovieSubType::getMovieId);
        if (typeId != 0) mstQw.eq(MovieSubType::getTypeId,typeId);
        List<MovieSubType> mstList = movieSubTypeDao.selectList(mstQw);

        System.out.println(mscList.size());
        System.out.println(mstList.size());

        List<Integer> movieIds = findIntersection(mstList,mscList);
        List<Movie> movieList = new ArrayList<>();

        String yearStr = DataParse.yearById(yearId);
        if (yearStr != null){
            for (int movieId:movieIds){
                Movie movie = movieDao.selectById(movieId);
                if (movie.getReleaseTime().equals(yearStr)) movieList.add(movie);
            }
            return movieList;
        }
        if (yearId == 0){
            for (int movieId:movieIds){
                Movie movie = movieDao.selectById(movieId);
                movieList.add(movie);
            }
            return movieList;
        }
        for (int movieId:movieIds){
            Movie movie = movieDao.selectById(movieId);
            int yearNum = Integer.parseInt(movie.getReleaseTime());
            if (yearNum<=2018) movieList.add(movie);
        }
        return movieList;
    }
}
