package cn.itcast.movie_back_end.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {
    boolean isValid;
    String token;
    String role;
}
