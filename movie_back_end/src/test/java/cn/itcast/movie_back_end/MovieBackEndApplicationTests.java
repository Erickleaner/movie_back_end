package cn.itcast.movie_back_end;

import cn.itcast.movie_back_end.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieBackEndApplicationTests {

    @Autowired
    BookService bookService;

    @Test
    void contextLoads() {
        bookService.getById(1);
    }

}
