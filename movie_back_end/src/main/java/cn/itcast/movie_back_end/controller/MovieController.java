package cn.itcast.movie_back_end.controller;


import cn.itcast.movie_back_end.dao.CountryDao;
import cn.itcast.movie_back_end.dao.MovieDao;
import cn.itcast.movie_back_end.dao.TypeDao;
import cn.itcast.movie_back_end.domain.dto.MovieQuery;
import cn.itcast.movie_back_end.domain.pojo.Country;
import cn.itcast.movie_back_end.domain.pojo.Movie;
import cn.itcast.movie_back_end.domain.pojo.Type;
import cn.itcast.movie_back_end.domain.vo.CheckGroups;
import cn.itcast.movie_back_end.domain.vo.PageGP;
import cn.itcast.movie_back_end.domain.vo.MovieVo;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.service.MovieService;
import cn.itcast.movie_back_end.service.MovieSubCountryService;
import cn.itcast.movie_back_end.service.MovieSubTypeService;
import cn.itcast.movie_back_end.util.AuthAccess;
import cn.itcast.movie_back_end.util.Code;
import cn.itcast.movie_back_end.util.DataParse;
import cn.itcast.movie_back_end.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieDao movieDao;

    @Autowired
    MovieSubCountryService movieSubCountryService;

    @Autowired
    MovieSubTypeService movieSubTypeService;

    @Autowired
    CountryDao countryDao;
    @Autowired
    TypeDao typeDao;

    @Autowired
    MovieService movieService;

    @AuthAccess
    @PostMapping ("/list")
    public Result getAll(@RequestBody MovieQuery movieQuery){
        movieQuery.setSort("+id");
        movieQuery.setName("");
        PageGP<MovieVo> pageGP = movieService.searchByTags(movieQuery);
        return new Result(Code.REQUEST_OK,pageGP);

    }

    @AuthAccess
    @GetMapping ("/checkGroups")
    public Result getCheckGroups(){
        CheckGroups checkGroups = new CheckGroups();
        List<Country> countries = countryDao.selectList(new QueryWrapper<>());
        countries.add(0, new Country(0,"全部"));
        List<Type> types = typeDao.selectList(new QueryWrapper<>());
        types.add(0, new Type(0,"全部"));
        checkGroups.setCountries(DataParse.countriesToTags(countries));
        checkGroups.setTypes(DataParse.typesToTags(types));
        return new Result(Code.REQUEST_OK,checkGroups);
    }

    @PutMapping
    public Result update(@RequestBody Movie movie){
        boolean flag = movieDao.updateById(movie) == 1;
        if (!flag) throw new BusinessException(Code.UPDATE_ERR,"更新失败！");
        return new Result(Code.REQUEST_OK, true);
    }

    @PostMapping
    public Result insert(@RequestBody Movie movie){
        boolean flag = movieDao.insert(movie) == 1;
        if (!flag) throw new BusinessException(Code.INSERT_ERR,"增加失败！");
        return new Result(Code.REQUEST_OK, movie.getMovieId());
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Integer id){
        boolean flag = movieDao.deleteById(id) == 1;
        if (!flag) throw new BusinessException(Code.INSERT_ERR,"删除失败！");
        return new Result(Code.REQUEST_OK, true);
    }

}
