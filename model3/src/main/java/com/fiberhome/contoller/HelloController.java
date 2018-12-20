package com.fiberhome.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanzhao zhang
 * @date 2018/12/18 11:06
 * Description:
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello world!";
    }

    @GetMapping("/hi")
    public String hi(){
        return "hi, 张三！";
    }


}
