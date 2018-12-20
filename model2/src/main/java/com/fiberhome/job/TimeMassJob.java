package com.fiberhome.job;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.Date;
import java.util.Set;

/**
 * 功能:定时群发工作任务
 * @author WH1707008
 */
public class TimeMassJob implements org.quartz.Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println(context.getJobDetail().getKey().getName()+"运行于:"+new Date());
    }
}
