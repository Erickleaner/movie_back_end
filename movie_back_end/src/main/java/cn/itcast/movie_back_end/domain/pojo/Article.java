package cn.itcast.movie_back_end.domain.pojo;

import cn.itcast.movie_back_end.util.TimestampToLongTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class Article {
    @TableId(type = IdType.AUTO)
    int articleId;
    @TableField(typeHandler = TimestampToLongTypeHandler.class)
    Long timestamp;
    String author;
    String reviewer;
    String title;
    int importance;
    String type;
    String status;
}
