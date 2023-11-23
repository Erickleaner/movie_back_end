package cn.itcast.movie_back_end.dao;

import cn.itcast.movie_back_end.domain.pojo.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MovieDao extends BaseMapper<Movie> {
    public Page<Integer> selectByTypeAndCountry(IPage<Integer> rowPage,
                                         @Param("name") String name,
                                         @Param("typeId") int typeId,
                                         @Param("countryId") int countryId,
                                         @Param("year") int year,
                                         @Param("sort") String sort);
    Page<Integer> selectByType(IPage<Integer> rowPage,
                               @Param("name") String name,
                               @Param("typeId") int typeId,
                               @Param("year") int year,
                               @Param("sort") String sort);

    Page<Integer> selectByCountry(IPage<Integer> rowPage,
                                  @Param("name") String name,
                                  @Param("countryId") int countryId,
                                  @Param("year") int year,
                                  @Param("sort") String sort);

    Page<Integer> selectByYear(IPage<Integer> rowPage,
                               @Param("name") String name,
                               @Param("year") int year,
                               @Param("sort") String sort);
}
