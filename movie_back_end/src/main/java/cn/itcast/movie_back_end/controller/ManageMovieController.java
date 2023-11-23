package cn.itcast.movie_back_end.controller;

import cn.itcast.movie_back_end.domain.dto.ManageMovieQuery;
import cn.itcast.movie_back_end.domain.pojo.Country;
import cn.itcast.movie_back_end.domain.pojo.Type;
import cn.itcast.movie_back_end.domain.vo.MovieVo;
import cn.itcast.movie_back_end.domain.vo.PageGP;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.service.CountryService;
import cn.itcast.movie_back_end.service.MovieService;
import cn.itcast.movie_back_end.service.TypeService;
import cn.itcast.movie_back_end.util.Code;
import cn.itcast.movie_back_end.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager-movie")
public class ManageMovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    TypeService typeService;

    @Autowired
    CountryService countryService;

    @GetMapping("/countries")
    public Result countries(){
        List<Country> list = countryService.list();
        list.add(0,new Country(0,"全部"));
        return new Result(Code.REQUEST_OK,list);
    }

    @GetMapping("/types")
    public Result types(){
        List<Type> list = typeService.list();
        list.add(0,new Type(0,"全部"));
        return new Result(Code.REQUEST_OK,list);
    }

    @PostMapping("/list")
    public Result getAll(@RequestBody ManageMovieQuery movieQuery){

        PageGP<MovieVo> pageGP = movieService.searchByQuery(movieQuery);
        return new Result(Code.REQUEST_OK,pageGP);

    }

    @PutMapping
    public Result update(@RequestBody MovieVo movieVo){
        boolean flag = movieService.update(movieVo);
        if (!flag) throw new BusinessException(Code.UPDATE_ERR,"更新失败！");
        return new Result(Code.REQUEST_OK, true);
    }

    @PostMapping
    public Result insert(@RequestBody MovieVo movieVo){
        boolean flag = movieService.insert(movieVo);
        if (!flag) throw new BusinessException(Code.UPDATE_ERR,"添加失败！");
        return new Result(Code.REQUEST_OK, movieVo.getMovieId());
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Integer id){
        boolean flag = movieService.remove(id);
        if (!flag) throw new BusinessException(Code.INSERT_ERR,"删除失败！");
        return new Result(Code.REQUEST_OK, true);
    }
}
