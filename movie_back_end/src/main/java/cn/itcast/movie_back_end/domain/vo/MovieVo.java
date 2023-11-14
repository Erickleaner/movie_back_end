package cn.itcast.movie_back_end.domain.vo;

import cn.itcast.movie_back_end.domain.pojo.Country;
import cn.itcast.movie_back_end.domain.pojo.Type;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieVo {
    int movieId;
    String link;
    String imgSrc;
    Float rating;
    int judgeNum;
    String inq;
    String titleCN;
    String titleEN;
    String director;
    String actor;
    String releaseTime;
    List<Country> countries;
    List<Type> types;
}
