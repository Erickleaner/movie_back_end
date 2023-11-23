package cn.itcast.movie_back_end.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManageMovieQuery {
    int page;
    int limit;
    String name;
    int countryId;
    int typeId;
    String sort;
}
