package cn.itcast.movie_back_end.dao;

import cn.itcast.movie_back_end.MovieBackEndApplication;
import cn.itcast.movie_back_end.domain.pojo.Article;
import cn.itcast.movie_back_end.domain.vo.ArticlePageGP;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = MovieBackEndApplication.class)
public class ArticleDaoTest {

    @Autowired
    ArticleDao articleDao;

    @Test
    public void mockData(){
        //insert需要提供的实体id为0
        //insert对应实体类要配置自增的策略否则拿不到返回的自增id
        Date currentDate = new Date();

        Article article = new Article();
        article.setTimestamp(currentDate.getTime());
        article.setAuthor("Erick");
        article.setReviewer("John");
        article.setTitle("title");
        article.setImportance(2);
        article.setType("CN");
        article.setStatus("published");

        for (int i=0;i<1;i++){
            articleDao.insert(article);
            System.out.println(article.getArticleId());
        }
    }

    @Test
    public void list(){
        ArticlePageGP articlePageGP = new ArticlePageGP();
        IPage iPage = new Page(2,10);
        articleDao.selectPage(iPage,null);
        articlePageGP.setItems(iPage.getRecords());
        articlePageGP.setTotal(iPage.getTotal());
    }

    @Test
    public void testUpdate(){
        List<Article> articles = articleDao.selectList(null);
        Article article = articles.get(0);
        article.setStatus("newStatus");
        articleDao.updateById(article);
    }

    @Test
    public void testInsert(){

        List<Article> articles = articleDao.selectList(null);
        Article article = articles.get(0);
        article.setArticleId(1);
        articleDao.insert(article);
        System.out.println(article.getArticleId());
    }
}
