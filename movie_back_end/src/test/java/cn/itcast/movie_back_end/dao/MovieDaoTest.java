package cn.itcast.movie_back_end.dao;


import cn.itcast.movie_back_end.MovieBackEndApplication;
import cn.itcast.movie_back_end.domain.dto.MovieQuery;
import cn.itcast.movie_back_end.domain.vo.MovieVo;
import cn.itcast.movie_back_end.domain.vo.PageGP;
import cn.itcast.movie_back_end.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = MovieBackEndApplication.class)
public class MovieDaoTest {

    @Autowired
    MovieService movieService;

    @Test
    public void selectByTags() {
        MovieQuery query = new MovieQuery();
        query.setPage(1);
        query.setLimit(5);
        query.setCountryId(0);
        query.setTypeId(0);
        query.setYearId(0);
        query.setSort("+id");
        PageGP<MovieVo> pageGP = movieService.searchByTags(query);
        List<MovieVo> items = pageGP.getItems();
        for (MovieVo item:items){
            System.out.println(item.getMovieId());
        }
    }
}
