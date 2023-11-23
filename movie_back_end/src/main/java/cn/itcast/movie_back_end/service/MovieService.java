package cn.itcast.movie_back_end.service;

import cn.itcast.movie_back_end.domain.dto.ManageMovieQuery;
import cn.itcast.movie_back_end.domain.dto.MovieQuery;
import cn.itcast.movie_back_end.domain.pojo.Movie;
import cn.itcast.movie_back_end.domain.vo.MovieVo;
import cn.itcast.movie_back_end.domain.vo.PageGP;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MovieService extends IService<Movie> {
    PageGP<MovieVo> searchByTags(MovieQuery movieQuery);

    PageGP<MovieVo> searchByQuery(ManageMovieQuery movieQuery);

    boolean insert(MovieVo movieVo);

    boolean update(Movie movie);

    boolean remove(Integer movieId);

    boolean update(MovieVo movieVo);
}
