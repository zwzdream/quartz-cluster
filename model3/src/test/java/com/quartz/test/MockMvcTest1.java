package com.quartz.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 *SpringRunner已继承SpringJUnit4ClassRunner，
 * 所以@RunWith(SpringRunner.class)与@RunWith(SpringJUnit4ClassRunner.class)功能一样
 * springboot提供@AutoConfigureMockMvc开启使用MockMvc的注解
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTest1 {
	/**
	 * 模拟mvc测试对象
	 */
	@Autowired
	private MockMvc mockMvc;




}
