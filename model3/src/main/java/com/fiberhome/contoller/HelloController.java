package com.fiberhome.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String hi(@RequestParam(value = "name",defaultValue = "张三") String name){
        return "hi,"+name+"!";
    }


}
