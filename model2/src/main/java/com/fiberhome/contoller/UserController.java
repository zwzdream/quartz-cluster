package com.fiberhome.contoller;

import com.fiberhome.job.TimeMassJob;
import com.fiberhome.pojo.User;
import com.fiberhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanzhao zhang
 * @date 2018/12/18 13:51
 * Description:
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("findById")
    public User findById(int id){
        return userService.findById(id);
    }

}
