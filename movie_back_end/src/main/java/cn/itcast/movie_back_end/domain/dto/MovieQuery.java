package cn.itcast.movie_back_end.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieQuery {
    int page;
    int limit;
    int countryId;
    int typeId;
    int yearId;
    int sortId;
}
