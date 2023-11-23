package cn.itcast.movie_back_end.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonInfo {
    String role;
    String introduction;
    String avatar;
    String name;
}
