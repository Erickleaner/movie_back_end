package cn.itcast.movie_back_end.controller;


import cn.itcast.movie_back_end.dao.ArticleDao;
import cn.itcast.movie_back_end.domain.dto.ArticleQuery;
import cn.itcast.movie_back_end.domain.pojo.Article;
import cn.itcast.movie_back_end.domain.vo.ArticlePageGP;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.util.Code;
import cn.itcast.movie_back_end.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleDao articleDao;

    @PostMapping ("/list")
    public Result getAll(@RequestBody ArticleQuery articleQuery){
        int page = articleQuery.getPage();
        int limit = articleQuery.getLimit();
        String type = articleQuery.getType();
        Integer importance = articleQuery.getImportance();
        String title = articleQuery.getTitle();
        String sort = articleQuery.getSort();

        ArticlePageGP articlePageGP = new ArticlePageGP();
        IPage<Article> iPage = new Page<>(page,limit);

        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        if (type!=null) qw.eq(Article::getType,type);
        if (importance!=null) qw.eq(Article::getImportance,importance);
        if (title!=null) qw.like(Article::getTitle,title);
        if (sort.equals("+id")) qw.orderByAsc(Article::getArticleId);
        if (sort.equals("-id")) qw.orderByDesc(Article::getArticleId);

        articleDao.selectPage(iPage,qw);
        articlePageGP.setItems(iPage.getRecords());
        articlePageGP.setTotal(iPage.getTotal());
        return new Result(Code.REQUEST_OK,articlePageGP);
    }

    @PutMapping
    public Result update(@RequestBody Article article){
        boolean flag = articleDao.updateById(article) == 1;
        if (!flag) throw new BusinessException(Code.UPDATE_ERR,"更新失败！");
        return new Result(Code.REQUEST_OK, true);
    }

    @PostMapping
    public Result insert(@RequestBody Article article){
        boolean flag = articleDao.insert(article) == 1;
        if (!flag) throw new BusinessException(Code.INSERT_ERR,"增加失败！");
        return new Result(Code.REQUEST_OK, article.getArticleId());
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Integer id){
        boolean flag = articleDao.deleteById(id) == 1;
        if (!flag) throw new BusinessException(Code.INSERT_ERR,"删除失败！");
        return new Result(Code.REQUEST_OK, true);
    }

}
