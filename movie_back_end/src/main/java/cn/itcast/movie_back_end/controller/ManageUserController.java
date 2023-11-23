package cn.itcast.movie_back_end.controller;

import cn.itcast.movie_back_end.domain.dto.UserQuery;
import cn.itcast.movie_back_end.domain.pojo.User;
import cn.itcast.movie_back_end.domain.vo.PageGP;
import cn.itcast.movie_back_end.exception.BusinessException;
import cn.itcast.movie_back_end.service.UserService;
import cn.itcast.movie_back_end.util.AuthAccess;
import cn.itcast.movie_back_end.util.Code;
import cn.itcast.movie_back_end.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager-user")
public class ManageUserController {
    @Autowired
    UserService userService;


    @PostMapping("/list")
    public Result getAll(@RequestBody UserQuery userQuery){

        PageGP<User> pageGP = userService.searchByQuery(userQuery);
        return new Result(Code.REQUEST_OK,pageGP);

    }

    @PutMapping
    public Result update(@RequestBody User user){
        boolean flag = userService.update(user);
        if (!flag) throw new BusinessException(Code.UPDATE_ERR,"更新失败！");
        return new Result(Code.REQUEST_OK, true);
    }

    @PostMapping
    public Result insert(@RequestBody User user){
        boolean flag = userService.insert(user);
        if (!flag) throw new BusinessException(Code.UPDATE_ERR,"添加失败！");
        return new Result(Code.REQUEST_OK, user.getUserId());
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Integer id){
        boolean flag = userService.removeById(id);
        if (!flag) throw new BusinessException(Code.INSERT_ERR,"删除失败！");
        return new Result(Code.REQUEST_OK, true);
    }
}
