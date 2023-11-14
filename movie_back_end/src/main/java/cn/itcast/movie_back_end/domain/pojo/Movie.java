package cn.itcast.movie_back_end.domain.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @TableId(type = IdType.AUTO)
    int movieId;
    String link;
    String imgSrc;
    Float rating;
    int judgeNum;
    String inq;
    @TableField(value = "titleCN")
    String titleCN;
    @TableField(value = "titleEN")
    String titleEN;
    String director;
    String actor;
    String releaseTime;

}
