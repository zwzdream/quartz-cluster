package com.quartz.test;

import com.fiberhome.Application3;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author wanzhao zhang
 * @date 2018/12/20 17:00
 * Description:
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application3.class)
public class MockMvcTest2 {
    /**
     * 模拟mvc测试对象
     */
    private MockMvc mockMvc;
    /**
     * web项目上下文
     */
    @Autowired
    private WebApplicationContext webApplicationContext;
    /**
     * 所有测试方法执行之前执行该方法
     */
    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addJob() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8021/user/add?job_name=model3_quartz1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(equalTo("请观察控制台！")))
                .andDo(MockMvcResultHandlers.print());

     /*   mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8021/hi?name=李四"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string(equalTo("hi,李四!")))
                .andDo(MockMvcResultHandlers.print());*/

    }
}
