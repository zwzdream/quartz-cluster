package com.fiberhome.job;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.Set;

/**
 * 功能:定时群发工作任务
 * @author WH1707008
 * 继承QuartzJobBean后，可以定义setter来注入数据映射属性、spring IoC管理的bean
 */
public class TimeMassJob extends QuartzJobBean {

    /*
    private MyService myService;

    private String name;

    // Inject "MyService" bean
    public void setMyService(MyService myService) { ... }

    // Inject the "name" job data property
    public void setName(String name) { ... }

    */

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println(context.getJobDetail().getKey().getName()+"运行于:"+new Date());
    }


}
