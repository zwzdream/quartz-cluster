package com.fiberhome.service;

import com.fiberhome.dao.IUserDao;
import com.fiberhome.pojo.User;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wanzhao zhang
 * @date 2018/12/18 13:49
 * Description:
 */
@Service("userService")
public class UserService {
    @Resource
    private IUserDao userDao;

    public User findById(int id){
        return userDao.findById(id);
    }


}
