package cn.itcast.movie_back_end.service.Impl;
import cn.itcast.movie_back_end.domain.pojo.Type;
import cn.itcast.movie_back_end.domain.pojo.Country;

import cn.itcast.movie_back_end.dao.MovieDao;
import cn.itcast.movie_back_end.dao.MovieSubCountryDao;
import cn.itcast.movie_back_end.dao.MovieSubTypeDao;
import cn.itcast.movie_back_end.domain.dto.ManageMovieQuery;
import cn.itcast.movie_back_end.domain.dto.MovieQuery;
import cn.itcast.movie_back_end.domain.pojo.*;
import cn.itcast.movie_back_end.domain.vo.MovieVo;
import cn.itcast.movie_back_end.domain.vo.PageGP;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.service.MovieService;
import cn.itcast.movie_back_end.service.MovieSubCountryService;
import cn.itcast.movie_back_end.service.MovieSubTypeService;
import cn.itcast.movie_back_end.util.Code;
import cn.itcast.movie_back_end.util.DataParse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl extends ServiceImpl<MovieDao, Movie>
        implements MovieService {
    @Autowired
    MovieDao movieDao;

    @Autowired
    MovieSubCountryDao movieSubCountryDao;

    @Autowired
    MovieSubTypeDao movieSubTypeDao;

    @Autowired
    MovieSubCountryService movieSubCountryService;

    @Autowired
    MovieSubTypeService movieSubTypeService;


    @Override
    public PageGP<MovieVo> searchByTags(MovieQuery movieQuery) {

        int page = movieQuery.getPage();
        int limit = movieQuery.getLimit();

        int typeId = movieQuery.getTypeId();
        int countryId = movieQuery.getCountryId();
        int yearId = movieQuery.getYearId();
        int year = DataParse.yearById(yearId);
        String sort = movieQuery.getSort();
        String name = movieQuery.getName();


        PageGP<MovieVo> pageGP = new PageGP<>();

        if (typeId == 0 && countryId ==0){
            IPage<Integer> iPage = new Page<>(page,limit);
            movieDao.selectByYear(iPage,name,year,sort);
            List<Integer> movieIds = iPage.getRecords();
            List<Movie> movieList = getMovieList(movieIds);
            List<MovieVo> movieVoList = makeMovieVoList(movieList);
            pageGP.setItems(movieVoList);
            pageGP.setTotal(iPage.getTotal());
        }


        if (typeId !=0 && countryId == 0){
            IPage<Integer> iPage = new Page<>(page,limit);
            movieDao.selectByType(iPage,"",typeId,year,sort);
            List<Integer> movieIds = iPage.getRecords();
            List<Movie> movieList = getMovieList(movieIds);
            List<MovieVo> movieVoList = makeMovieVoList(movieList);
            pageGP.setItems(movieVoList);
            pageGP.setTotal(iPage.getTotal());
        }

        if (typeId ==0 && countryId != 0){
            IPage<Integer> iPage = new Page<>(page,limit);
            movieDao.selectByCountry(iPage,name,countryId,year,sort);
            List<Integer> movieIds = iPage.getRecords();
            List<Movie> movieList = getMovieList(movieIds);
            List<MovieVo> movieVoList = makeMovieVoList(movieList);
            pageGP.setItems(movieVoList);
            pageGP.setTotal(iPage.getTotal());
        }

        if (typeId !=0 && countryId != 0){
            IPage<Integer> iPage = new Page<>(page,limit);
            movieDao.selectByTypeAndCountry(iPage,name,typeId,countryId,year,sort);
            List<Integer> movieIds = iPage.getRecords();
            List<Movie> movieList = getMovieList(movieIds);
            List<MovieVo> movieVoList = makeMovieVoList(movieList);
            pageGP.setItems(movieVoList);
            pageGP.setTotal(iPage.getTotal());
        }

        return pageGP;
    }

    @Override
    public PageGP<MovieVo> searchByQuery(ManageMovieQuery movieQuery) {
        int page = movieQuery.getPage();
        int limit = movieQuery.getLimit();
        String name = movieQuery.getName();
        int countryId = movieQuery.getCountryId();
        int typeId = movieQuery.getTypeId();
        String sort = movieQuery.getSort();

        MovieQuery query = new MovieQuery();
        query.setPage(page);
        query.setLimit(limit);
        query.setCountryId(countryId);
        query.setTypeId(typeId);
        query.setYearId(0);
        query.setSort(sort);
        query.setName(name);

        return searchByTags(query);
    }

    @Override
    public boolean insert(MovieVo movieVo) {
        String link = movieVo.getLink();
        String imgSrc = movieVo.getImgSrc();
        Float rating = movieVo.getRating();
        int judgeNum = movieVo.getJudgeNum();
        String inq = movieVo.getInq();
        String titleCN = movieVo.getTitleCN();
        String titleEN = movieVo.getTitleEN();
        String director = movieVo.getDirector();
        String actor = movieVo.getActor();
        String releaseTime = movieVo.getReleaseTime();

        List<Type> types = movieVo.getTypes();
        List<Country> countries = movieVo.getCountries();

        if (types.size() == 0) throw new BusinessException(Code.BUSINESS_ERR,"电影标签不能为空");
        if (countries.size() == 0) throw new BusinessException(Code.BUSINESS_ERR,"电影拍摄国家不能为空");
        //这里应该使用业务保证操作一致性
        Movie movie = new Movie(0,link,imgSrc,rating,judgeNum,inq,titleCN,titleEN,
                director,actor,releaseTime);
        this.save(movie);
        int id = movie.getMovieId();
        addTypesAndCountries(id,types,countries);
        movieVo.setMovieId(id);
        return true;
    }

    @Override
    public boolean update(Movie movie) {
        return false;
    }

    //此处应该使用事务
    @Override
    public boolean remove(Integer movieId) {
        boolean isRemove = clearTypesAndCountries(movieId);
        if (!isRemove) return false;
        return this.removeById(movieId);
    }

    public boolean clearTypesAndCountries(int movieId){
        boolean isRemove;
        LambdaQueryWrapper<MovieSubType> mstQw = new LambdaQueryWrapper<>();
        mstQw.eq(MovieSubType::getMovieId,movieId);
        isRemove = movieSubTypeService.remove(mstQw);
        if (!isRemove) return false;

        LambdaQueryWrapper<MovieSubCountry> mscQw = new LambdaQueryWrapper<>();
        mscQw.eq(MovieSubCountry::getMovieId,movieId);
        isRemove = movieSubCountryService.remove(mscQw);
        return isRemove;
    }

    public void addTypesAndCountries(int movieId,List<Type> types,List<Country> countries){
        for (Type type:types){
            int typeId = type.getTypeId();
            MovieSubType movieSubType = new MovieSubType();
            movieSubType.setMovieId(movieId);
            movieSubType.setTypeId(typeId);
            movieSubTypeService.save(movieSubType);
        }
        for (Country country:countries){
            int countryId = country.getCountryId();
            MovieSubCountry movieSubCountry = new MovieSubCountry();
            movieSubCountry.setMovieId(movieId);
            movieSubCountry.setCountryId(countryId);
            movieSubCountryService.save(movieSubCountry);
        }
    }

    //全删除再全加入那么Id就变了..
    @Override
    public boolean update(MovieVo movieVo) {

        int movieId = movieVo.getMovieId();
        String link = movieVo.getLink();
        String imgSrc = movieVo.getImgSrc();
        Float rating = movieVo.getRating();
        int judgeNum = movieVo.getJudgeNum();
        String inq = movieVo.getInq();
        String titleCN = movieVo.getTitleCN();
        String titleEN = movieVo.getTitleEN();
        String director = movieVo.getDirector();
        String actor = movieVo.getActor();
        String releaseTime = movieVo.getReleaseTime();
        List<Country> countries = movieVo.getCountries();
        List<Type> types = movieVo.getTypes();

        if (types.size() == 0) throw new BusinessException(Code.BUSINESS_ERR,"电影标签不能为空");
        if (countries.size() == 0) throw new BusinessException(Code.BUSINESS_ERR,"电影拍摄国家不能为空");
        Movie movie = new Movie(movieId,link,imgSrc,rating,judgeNum,inq,titleCN,titleEN,
                director,actor,releaseTime);

        boolean isUpdate;
        isUpdate = clearTypesAndCountries(movieId);
        if (!isUpdate) return false;

        isUpdate = this.updateById(movie);
        if (!isUpdate) return false;

        addTypesAndCountries(movieId,types,countries);

        return true;
    }

    private List<Movie> getMovieList(List<Integer> ids){
        List<Movie> movieList = new ArrayList<>();
        for (Integer id:ids){
            Movie movie = movieDao.selectById(id);
            movieList.add(movie);
        }
        return movieList;
    }

    private List<MovieVo> makeMovieVoList(List<Movie> movieList){
        List<MovieVo> movieVoList = new ArrayList<>();
        for (Movie movie:movieList){
            int movieId = movie.getMovieId();
            String link = movie.getLink();
            String imgSrc = movie.getImgSrc();
            Float rating = movie.getRating();
            int judgeNum = movie.getJudgeNum();
            String inq = movie.getInq();
            String titleCN = movie.getTitleCN();
            String titleEN = movie.getTitleEN();
            String director = movie.getDirector();
            String actor = movie.getActor();
            String releaseTime = movie.getReleaseTime();

            List<Country> countries = movieSubCountryService.getCountriesByMovieId(movieId);
            List<Type> types = movieSubTypeService.getTypesByMovieId(movieId);


            MovieVo movieVo = new MovieVo(movieId,link,imgSrc,rating,judgeNum,inq,titleCN,
                    titleEN,director,actor,releaseTime,countries,types);
            movieVoList.add(movieVo);
        }
        return movieVoList;
    }

}
