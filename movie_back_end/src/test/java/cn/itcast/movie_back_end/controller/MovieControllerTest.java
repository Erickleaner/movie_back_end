package cn.itcast.movie_back_end.controller;

import cn.itcast.movie_back_end.MovieBackEndApplication;
import cn.itcast.movie_back_end.dao.MovieDao;
import cn.itcast.movie_back_end.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = MovieBackEndApplication.class)
public class MovieControllerTest extends MovieController{

    @Autowired
    MovieDao movieDao;

    @Autowired
    MovieService movieService;


    @Test
    public void searchAll(){
        //movieService.searchByTags(0,0,0);
    }

    @Test
    public void getAll() {
        /*MovieQuery movieQuery = new MovieQuery(1,9);
        int page = movieQuery.getPage();
        int limit = movieQuery.getLimit();
        MoviePageGP MoviePageGP = new MoviePageGP();
        IPage<Movie> iPage = new Page<>(page,limit);

        LambdaQueryWrapper<Movie> qw = new LambdaQueryWrapper<>();
        qw.orderByAsc(Movie::getMovieId);

        movieDao.selectPage(iPage,qw);
        List<Movie> movieList = iPage.getRecords();
        List<MovieVo> movieVoList = makeMovieVoList(movieList);
        MoviePageGP.setItems(movieVoList);
        MoviePageGP.setTotal(iPage.getTotal());
        for (int i=0;i<movieVoList.size();i++){
            System.out.println(movieVoList.get(i).toString());
        }*/
    }
}
