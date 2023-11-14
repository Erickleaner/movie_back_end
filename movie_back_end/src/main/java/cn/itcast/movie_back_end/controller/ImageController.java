package cn.itcast.movie_back_end.controller;

import cn.itcast.movie_back_end.dao.MovieDao;
import cn.itcast.movie_back_end.domain.pojo.Article;
import cn.itcast.movie_back_end.domain.pojo.Movie;
import cn.itcast.movie_back_end.util.AuthAccess;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    MovieDao movieDao;

    //后端支持解析获取image
    @AuthAccess
    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable Integer id) {
        // if movie == null
        Movie movie = movieDao.selectById(id);
        if (movie == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        String imageUrl = movie.getImgSrc();
        try {
            // 使用 URLConnection 获取图片内容
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 根据实际图片类型设置 MediaType

            // 构建 ResponseEntity
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
