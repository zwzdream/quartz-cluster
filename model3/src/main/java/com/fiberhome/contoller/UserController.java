package com.fiberhome.contoller;

import com.fiberhome.job.TimeMassJob;
import com.fiberhome.pojo.User;
import com.fiberhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanzhao zhang
 * @date 2018/12/18 13:51
 * Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/findById")
    public User findById(@RequestParam(value = "id",defaultValue = "1")int id){
        return userService.findById(id);
    }

    /**该方法是为了测试UserService服务类中可以添加Job
     **/
    @GetMapping("add")
    public String  addQuartz(String job_name){
        //String job_name = "job1";
        //String s=userService.addJob(job_name, job_name, job_name, job_name, TimeMassJob.class, "0/10 * * * * ?", new Object[]{});
        //System.out.println(s);
        return "请观察控制台！";
    }

    @GetMapping("addJob")
    public String  add(String job_name){
        String s=userService.addJob2(job_name);
        System.out.println(s);
        return "请观察控制台2！";
    }


}
