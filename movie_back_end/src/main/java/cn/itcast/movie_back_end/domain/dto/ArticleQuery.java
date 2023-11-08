package cn.itcast.movie_back_end.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQuery {
    int page;
    int limit;
    Integer importance;
    String title;
    String type;
    String sort;
}
