package cn.itcast.movie_back_end.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSubType {
    @TableId(type = IdType.AUTO)
    int movieSubTypeId;
    int movieId;
    int typeId;
}
