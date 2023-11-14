package cn.itcast.movie_back_end.dao;

import cn.itcast.movie_back_end.domain.pojo.Movie;
import cn.itcast.movie_back_end.domain.pojo.MovieSubType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieSubTypeDao extends BaseMapper<MovieSubType> {
}
