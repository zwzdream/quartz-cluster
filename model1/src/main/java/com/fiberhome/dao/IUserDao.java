package com.fiberhome.dao;

import com.fiberhome.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wanzhao zhang
 * @date 2018/12/18 13:46
 * Description:
 */
@Mapper
public interface IUserDao {

    User findById(@Param("id") int id);

}
