package com.fiberhome.job;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 功能:定时群发工作任务
 * @author WH1707008
 */
/*
*@PersistJobDataAfterExecution 防重复执行
* 当一个Job被添加到调度程序(任务执行计划表)scheduler的时候，JobDataMap实例就会存储一次关于该任务的状态信息数据。
* 也可以使用@PersistJobDataAfterExecution注解标明在一个任务执行完毕之后就存储一次
*@DisallowConcurrentExecution  防并发执行
*  使用该注解，它会告诉Quartz不要执行多个任务实例
 * */

public class TimeMassJob implements org.quartz.Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println(context.getJobDetail().getKey().getName()+"运行于:"+new Date());
    }
}
