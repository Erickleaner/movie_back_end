package cn.itcast.movie_back_end.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {
    int page;
    int limit;
    String sort;
    String username;
}
