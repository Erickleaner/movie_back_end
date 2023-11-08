package cn.itcast.movie_back_end.domain.vo;

import cn.itcast.movie_back_end.domain.pojo.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePageGP {

    List<Article> items;
    long total;

}
