package cn.itcast.movie_back_end.util;

import cn.itcast.movie_back_end.domain.pojo.Country;
import cn.itcast.movie_back_end.domain.pojo.Type;
import cn.itcast.movie_back_end.domain.vo.Tag;

import java.util.ArrayList;
import java.util.List;

public class DataParse {
    public static List<Tag> typesToTags(List<Type> types){
        List<Tag> tagList = new ArrayList<>();
        for (Type type:types){
            int typeId = type.getTypeId();
            String name = type.getName();
            tagList.add(new Tag(typeId,name));
        }
        return tagList;
    }
    public static List<Tag> countriesToTags(List<Country> countries){
        List<Tag> tagList = new ArrayList<>();
        for (Country country:countries){
            int countryId = country.getCountryId();
            String name = country.getName();
            tagList.add(new Tag(countryId,name));
        }
        return tagList;
    }
    public static int yearById(int yearId){
        //before 2018
        int[] arr = new int[]{0,2023,2022,2021,2020,2019,2018};
        if (yearId>0 && yearId<=6) return arr[yearId];
        return 0;
    }
}
